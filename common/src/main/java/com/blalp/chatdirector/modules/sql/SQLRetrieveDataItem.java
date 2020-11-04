package com.blalp.chatdirector.modules.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;
public class SQLRetrieveDataItem extends SQLItem {

    public SQLRetrieveDataItem(String table, String name, String key, String connectionName,boolean cache) {
        super(table, name, key, connectionName,cache);
    }

    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        if (cache&&SQLCacheStore.containsKey(connectionName,ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context))){
            this.context.put("SQL_RESULT", SQLCacheStore.getValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context)));
            this.context.put("CACHE_RESULT", SQLCacheStore.getValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context)));
        }
        try {
            PreparedStatement statement = SQLModule.connections.get(connectionName).connection.prepareStatement("SELECT `value` from "+ChatDirector.format(table, context)+" WHERE `name`=? AND `key`=? LIMIT 1");
            statement.setString(1, ChatDirector.format(name, context));
            statement.setString(2, ChatDirector.format(key, context));
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                this.context.put("SQL_RESULT", results.getString("value"));
                if(cache){
                    SQLCacheStore.setValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context), results.getString("value"));
                }
            } else {
                if(Configuration.debug){
                    System.out.println("No result was found ");
                }
            }
        } catch (SQLException e){
            System.err.println(this+" failed on "+string);
            if(Configuration.debug){
                System.out.println("Failed SQL "+e.getSQLState());
            }
            e.printStackTrace();
        }
        return string;
    }
    
}
