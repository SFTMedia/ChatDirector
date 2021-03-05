package com.blalp.chatdirector.extra.modules.sql;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

public class SQLModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("send-data", "retrieve-data", "sql-cache-if", "sql-cache-remove");
    }

    @Override
    public boolean load() {
        if(ChatDirector.getConfig().getModuleData()==null||ChatDirector.getConfig().getModuleData().get("sql")==null){
            if(ChatDirector.getConfig().hasDaemon(SQLConnections.class)){
                // Only spit out a warning if there were SQL items
                ChatDirector.getLogger().log(Level.WARNING, "Failed to load SQL module, no module_data");
            } else {
                // Of if debug mode is on
                ChatDirector.getLogger().log(Level.INFO, "Failed to load SQL module, no module_data. If you are not using SQL items, you can safely ignore this.");
            }
            return true;
        }
        SQLConnections connections = (SQLConnections) ChatDirector.getConfig().getOrCreateDaemon(SQLConnections.class);
        for (Entry<String,String> connection : ChatDirector.getConfig().getModuleData().get("sql").entrySet()) {
            connections.put(connection.getKey(), new SQLConnection(connection.getValue()));
        }
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
            case "send-data":
                return SQLSendDataItem.class;
            case "retrieve-data":
                return SQLRetrieveDataItem.class;
            case "sql-cache-if":
                return SQLCacheIfItem.class;
            case "sql-cache-remove":
                return SQLCacheRemoveItem.class;
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}
