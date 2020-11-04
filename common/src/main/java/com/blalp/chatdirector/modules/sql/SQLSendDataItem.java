package com.blalp.chatdirector.modules.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;
public class SQLSendDataItem extends SQLItem {
    String value;
    public SQLSendDataItem(String table, String name, String key, String connectionName,String value,boolean cache) {
        super(table, name, key, connectionName,cache);
        this.value=value;
    }

    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        if(cache) {
            SQLCacheStore.setValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context), ChatDirector.format(value,context));
        }
        try {
            PreparedStatement statement = SQLModule.connections.get(connectionName).connection.prepareStatement("INSERT INTO "+ChatDirector.format(table, context)+" (`name`,`key`,`value`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `value`=?");
            statement.setString(1, ChatDirector.format(name, context));
            statement.setString(2, ChatDirector.format(key, context));
            statement.setString(3, ChatDirector.format(value, context));
            statement.setString(4, ChatDirector.format(value, context));
            statement.execute();
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
