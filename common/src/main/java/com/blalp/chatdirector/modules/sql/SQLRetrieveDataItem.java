package com.blalp.chatdirector.modules.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
public class SQLRetrieveDataItem extends SQLItem {

    public SQLRetrieveDataItem(String table, String name, String key, String connectionName,boolean cache) {
        super(table, name, key, connectionName,cache);
    }

    @Override
    public Context process(Context context) {
        Context output = new Context();
        if (cache&&SQLCacheStore.containsKey(connectionName,ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context))){
            output.put("SQL_RESULT", SQLCacheStore.getValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context)));
            output.put("CACHE_RESULT", SQLCacheStore.getValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context)));
        } else {
            try {
                PreparedStatement statement = SQLModule.connections.get(connectionName).connection.prepareStatement("SELECT `value` from "+ChatDirector.format(table, context)+" WHERE `name`=? AND `key`=? LIMIT 1");
                statement.setString(1, ChatDirector.format(name, context));
                statement.setString(2, ChatDirector.format(key, context));
                ResultSet results = statement.executeQuery();
                if(results.next()) {
                    output.put("SQL_RESULT", results.getString("value"));
                    if(cache){
                        SQLCacheStore.setValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context), results.getString("value"));
                    }
                } else {
                    ChatDirector.logDebug("No result was found ");
                }
            } catch (SQLException e){
                System.err.println(this+" failed on "+context.getCurrent());
                ChatDirector.logDebug("Failed SQL "+e.getSQLState());
                e.printStackTrace();
            }
        }
        return output;
    }
    
}
