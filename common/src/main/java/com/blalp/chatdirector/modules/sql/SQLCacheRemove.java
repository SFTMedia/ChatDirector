package com.blalp.chatdirector.modules.sql;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;

public class SQLCacheRemove extends SQLItem {
    public SQLCacheRemove(String table, String name, String key, String connectionName, boolean cache) {
        super(table, name, key, connectionName, cache);
    }

    @Override
    public String process(String string, Map<String, String> context) {
        if(SQLCacheStore.containsKey(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context))) {
            SQLCacheStore.removeValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context));
            if (Configuration.debug){
                System.out.println("Cache Hit "+connectionName+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context)+", removing...");
            }
        } else {
            if (Configuration.debug){
                System.out.println("Cache Miss "+connectionName+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context));
            }
        }
        return string;
    }
}
