package com.blalp.chatdirector.extra.modules.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class SQLSendDataItem extends SQLItem {
    String value;
    private boolean attemptedReload = false;

    public SQLSendDataItem() {
        ((SQLConnections) ChatDirector.getConfigStaging().getOrCreateDaemon(SQLConnections.class)).addItem(this);
    }

    @Override
    public Context process(Context context) {
        if (cache) {
            SQLCacheStore sqlCacheStore = (SQLCacheStore) ChatDirector.getConfig()
                    .getOrCreateDaemon(SQLCacheStore.class);
            sqlCacheStore.setValue(connection, ChatDirector.format(table, context), ChatDirector.format(name, context),
                    ChatDirector.format(key, context), ChatDirector.format(value, context));
        }
        SQLConnection connectionObj = ((SQLConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(SQLConnections.class)).get(connection);
        try {
            PreparedStatement statement = connectionObj.getConnection()
                    .prepareStatement("INSERT INTO " + ChatDirector.format(table, context)
                            + " (`name`,`key`,`value`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `value`=?");
            statement.setString(1, ChatDirector.format(name, context));
            statement.setString(2, ChatDirector.format(key, context));
            statement.setString(3, ChatDirector.format(value, context));
            statement.setString(4, ChatDirector.format(value, context));
            statement.execute();
        } catch (SQLException e) {
            System.err.println(this + " failed on " + context.getCurrent());
            ChatDirector.getLogger().log(Level.WARNING, "Failed SQL " + e.getSQLState());
            e.printStackTrace();
            if (!attemptedReload) {
                connectionObj.unload();
                connectionObj.load();
                this.process(context);
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(value) && super.isValid();
    }

}
