package com.blalp.chatdirector.modules.sql;

import java.util.Map;

import com.blalp.chatdirector.configuration.Configuration;

public class SQLCacheRemove extends SQLItem {
    public SQLCacheRemove(String table, String name, String key, String connectionName, boolean cache) {
        super(table, name, key, connectionName, cache);
    }

    @Override
    public String process(String string, Map<String, String> context) {
        if(SQLCacheStore.containsKey(connectionName, table, name, key)) {
            SQLCacheStore.removeValue(connectionName, table, name, key);
            if (Configuration.debug){
                System.out.println("Cache Hit "+connectionName+" "+table+" "+name+" "+key+", removing...");
            }
        } else {
            if (Configuration.debug){
                System.out.println("Cache Miss "+connectionName+" "+table+" "+name+" "+key);
            }
        }
        return string;
    }
}
