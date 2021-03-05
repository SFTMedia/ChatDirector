package com.blalp.chatdirector.extra.modules.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = SQLRetrieveDataDeserializer.class)
public class SQLRetrieveDataItem extends SQLItem {

    @Override
    public Context process(Context context) {
        Context output = new Context();
        if (cache && SQLCacheStore.containsKey(connection, ChatDirector.format(table, context),
                ChatDirector.format(name, context), ChatDirector.format(key, context))) {
            output.put("SQL_RESULT", SQLCacheStore.getValue(connection, ChatDirector.format(table, context),
                    ChatDirector.format(name, context), ChatDirector.format(key, context)));
            output.put("CACHE_RESULT", SQLCacheStore.getValue(connection, ChatDirector.format(table, context),
                    ChatDirector.format(name, context), ChatDirector.format(key, context)));
        } else {
            SQLConnection connectionObj = ((SQLConnections)ChatDirector.getConfig().getOrCreateDaemon(SQLConnections.class)).get(connection);
            try {
                PreparedStatement statement = connectionObj.getConnection()
                        .prepareStatement("SELECT `value` from " + ChatDirector.format(table, context)
                                + " WHERE `name`=? AND `key`=? LIMIT 1");
                statement.setString(1, ChatDirector.format(name, context));
                statement.setString(2, ChatDirector.format(key, context));
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    output.put("SQL_RESULT", results.getString("value"));
                    if (cache) {
                        SQLCacheStore.setValue(connection, ChatDirector.format(table, context),
                                ChatDirector.format(name, context), ChatDirector.format(key, context),
                                results.getString("value"));
                    }
                } else {
                    ChatDirector.getLogger().log(Level.WARNING, "No result was found ");
                }
            } catch (SQLException e) {
                System.err.println(this + " failed on " + context.getCurrent());
                ChatDirector.getLogger().log(Level.WARNING, "Failed SQL " + e.getSQLState());
                e.printStackTrace();
            }
        }
        return output;
    }

}
