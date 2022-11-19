package com.blalp.chatdirector.extra.modules.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = SQLSendDataDeserializer.class)
public class SQLSendDataItem extends SQLItem {
    String value;
    private long lastReload = 0;

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
            if (lastReload+1000<System.currentTimeMillis()) {
                lastReload=System.currentTimeMillis();
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
