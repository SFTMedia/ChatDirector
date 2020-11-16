package com.blalp.chatdirector.modules.sql;
import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class SQLCacheRemove extends SQLItem {
    public SQLCacheRemove(String table, String name, String key, String connectionName, boolean cache) {
        super(table, name, key, connectionName, cache);
    }

    @Override
    public Context process(Context context) {
        if(SQLCacheStore.containsKey(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context))) {
            SQLCacheStore.removeValue(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context));
            ChatDirector.logDebug("Cache Hit "+connectionName+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context)+", removing...");
        } else {
            ChatDirector.logDebug("Cache Miss "+connectionName+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context));
        }
        return new Context();
    }
}
