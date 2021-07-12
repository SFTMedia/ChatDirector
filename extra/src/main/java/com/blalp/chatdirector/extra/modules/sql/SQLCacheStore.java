package com.blalp.chatdirector.extra.modules.sql;

import java.util.concurrent.ConcurrentHashMap;

import com.blalp.chatdirector.core.model.IDaemon;
import com.blalp.chatdirector.core.model.IItem;

public class SQLCacheStore implements IDaemon {
    private ConcurrentHashMap<String, String> data = new ConcurrentHashMap<>();

    private String getKey(String connection, String table, String name, String key) {
        return connection + "/" + table + "/" + name + "/" + key;
    }

    public String getValue(String connection, String table, String name, String key) {
        if (data.containsKey(getKey(connection, table, name, key))) {
            return data.get(getKey(connection, table, name, key));
        }
        return "";
    }

    public void setValue(String connection, String table, String name, String key, String value) {
        data.put(getKey(connection, table, name, key), value);
    }

    public void containsKey() {
    }

    public boolean containsKey(String connection, String table, String name, String key) {
        return data.containsKey(getKey(connection, table, name, key));
    }

    public void removeValue(String connection, String table, String name, String key) {
        data.remove(getKey(connection, table, name, key), data.get(getKey(connection, table, name, key)));
    }

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        data = new ConcurrentHashMap<>();
        return true;
    }

    @Override
    public void addItem(IItem item) {
        // N/A
    }
}
