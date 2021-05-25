package com.blalp.chatdirector.extra.modules.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

public class SQLConnections extends HashMap<String, SQLConnection> implements IDaemon {
    Map<String, List<String>> tables = new HashMap<>();

    @Override
    public boolean load() {
        for (Entry<String, SQLConnection> connection : this.entrySet()) {
            if (tables.containsKey(connection.getKey())) {
                connection.getValue().addTables(tables.get(connection.getKey()));
            }
            if (!connection.getValue().load()) {
                ChatDirector.getLogger().log(Level.SEVERE, connection + " failed to load.");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (SQLConnection connection : this.values()) {
            if (!connection.unload()) {
                ChatDirector.getLogger().log(Level.WARNING, connection + " failed to unload.");
            }
        }
        return true;
    }

    @Override
    public void addItem(IItem item) {
        if (item instanceof SQLItem) {
            if (!tables.containsKey(((SQLItem) item).getConnection())) {
                tables.put(((SQLItem) item).getConnection(), new ArrayList<>());
            }
            tables.get(((SQLItem) item).getConnection()).add(((SQLItem) item).getTable());
        } else if (item instanceof SQLCacheIfItem) {
            if (!tables.containsKey(((SQLCacheIfItem) item).getConnection())) {
                tables.put(((SQLCacheIfItem) item).getConnection(), new ArrayList<>());
            }
            tables.get(((SQLCacheIfItem) item).getConnection()).add(((SQLCacheIfItem) item).getTable());
        }
        return;
    }

}
