package com.blalp.chatdirector.modules.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.blalp.chatdirector.configuration.Configuration;
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
        LinkedHashMap<String, Object> configMap = (LinkedHashMap<String, Object>) config;
        switch (type) {
            case "send-data":
                tables.get((String)configMap.get("connection")).add((String)configMap.get("table"));
                return new SQLSendDataItem((String)configMap.get("table"), (String)configMap.get("name"), (String)configMap.get("key"),
                (String)configMap.get("connection"), (String)configMap.get("value"), (boolean)configMap.get("cache"));
            case "retrieve-data":
                tables.get((String)configMap.get("connection")).add((String)configMap.get("table"));
                return new SQLRetrieveDataItem((String)configMap.get("table"), (String)configMap.get("name"), (String)configMap.get("key"),
                (String)configMap.get("connection"), (boolean)configMap.get("cache"));
            default:
                return null;
        }
    }

    @Override
    public void load() {
        if(Configuration.debug){
            System.out.println("Loading "+this);
        }
        for (Entry<String, SQLConnection> connection : connections.entrySet()) {
            connection.getValue().load();
            for (String table : tables.get(connection.getKey())) {
                try {
                    connection.getValue().connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+table+" (`name` varchar(255) NOT NULL,`key` varchar(255) NOT NULL, `value` varchar(255) NOT NULL, PRIMARY KEY (`key`, `name`));").execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unload() {
        if(Configuration.debug){
            System.out.println("Unloading "+this);
        }
        for(SQLConnection connection:connections.values()){
            connection.unload();
        }
    }
    
}
