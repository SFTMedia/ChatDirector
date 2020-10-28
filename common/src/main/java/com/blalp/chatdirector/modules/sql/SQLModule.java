package com.blalp.chatdirector.modules.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class SQLModule extends Module {
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
    public String[] getItemNames() {
        return new String[] { "send-data", "retrieve-data" };
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String, String> configMap = (LinkedHashMap<String, String>) config;
        switch (type) {
            case "send-data":
                tables.get(configMap.get("connection")).add(configMap.get("table"));
                return new SQLSendDataItem(configMap.get("table"), configMap.get("name"), configMap.get("key"),
                        configMap.get("connection"), configMap.get("value"));
            case "retrieve-data":
                tables.get(configMap.get("connection")).add(configMap.get("table"));
                return new SQLRetrieveDataItem(configMap.get("table"), configMap.get("name"), configMap.get("key"),
                        configMap.get("connection"));
            default:
                return null;
        }
    }

    @Override
    public void load() {
        for (Entry<String, SQLConnection> connection : connections.entrySet()) {
            connection.getValue().load();
            for (String table : tables.get(connection.getKey())) {
                try {
                    connection.getValue().connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS ? (name varchar(255),key varchar(255), value varchar(255));")
                            .execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unload() {
        for(SQLConnection connection:connections.values()){
            connection.unload();
        }
    }
    
}
