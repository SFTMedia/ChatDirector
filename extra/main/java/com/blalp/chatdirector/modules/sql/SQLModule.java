package com.blalp.chatdirector.modules.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IModule;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SQLModuleDeserializer.class)
public class SQLModule implements IModule {
    public static HashMap<String, SQLConnection> connections = new HashMap<>();
    public static HashMap<String, ArrayList<String>> tables = new HashMap<>();

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("send-data", "retrieve-data", "sql-cache-if", "sql-cache-remove");
    }

    @Override
    public boolean load() {
        ChatDirector.getLogger().log(Level.WARNING, "Loading " + this);
        for (Entry<String, SQLConnection> connection : connections.entrySet()) {
            connection.getValue().load();
            for (String table : tables.get(connection.getKey())) {
                try {
                    connection.getValue().connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table
                            + " (`name` varchar(255) NOT NULL,`key` varchar(255) NOT NULL, `value` varchar(255) NOT NULL, PRIMARY KEY (`key`, `name`));")
                            .execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean unload() {
        ChatDirector.getLogger().log(Level.WARNING, "Unloading " + this);
        for (SQLConnection connection : connections.values()) {
            connection.unload();
        }
        connections = new HashMap<>();
        tables = new HashMap<>();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Class<?> getItemClass(String type) {
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
