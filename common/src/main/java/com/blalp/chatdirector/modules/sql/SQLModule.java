package com.blalp.chatdirector.modules.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SQLModule implements IModule {
    public static HashMap<String, SQLConnection> connections = new HashMap<>();
    public static HashMap<String, ArrayList<String>> tables = new HashMap<>();

    public SQLModule(LinkedHashMap<String, LinkedHashMap<String, String>> map) {
        for (Entry<String, String> connectionEntry : map.get("connections").entrySet()) {
            SQLConnection connection = new SQLConnection(connectionEntry.getValue());
            connections.put(connectionEntry.getKey(), connection);
            tables.put(connectionEntry.getKey(), new ArrayList<>());
        }
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("send-data", "retrieve-data", "sql-cache-if", "sql-cache-remove");
    }

    @Override
    public void load() {
        ChatDirector.logDebug("Loading " + this);
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
    public void unload() {
        ChatDirector.logDebug("Unloading " + this);
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
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "send-data":
                tables.get(config.get("connection").asText()).add(config.get("table").asText());
                return new SQLSendDataItem(config.get("table").asText(), config.get("name").asText(),
                        config.get("key").asText(), config.get("connection").asText(),
                        config.get("value").asText(), config.get("cache").asBoolean());
            case "retrieve-data":
                tables.get(config.get("connection").asText()).add(config.get("table").asText());
                return new SQLRetrieveDataItem(config.get("table").asText(), config.get("name").asText(),
                        config.get("key").asText(), config.get("connection").asText(),
                        config.get("cache").asBoolean());
            case "sql-cache-if":
                return new SQLCacheIfItem(
                        ChatDirector.loadChain(om,config.get("yes-chain")),
                        ChatDirector.loadChain(om,config.get("no-chain")),
                        config.get("table").asText(), config.get("name").asText(), config.get("key").asText(),
                        config.get("connection").asText(), config.get("cache").asBoolean());
            case "sql-cache-remove":
                return new SQLCacheRemove(config.get("table").asText(), config.get("name").asText(),
                        config.get("key").asText(), config.get("connection").asText(),
                        config.get("cache").asBoolean());
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
