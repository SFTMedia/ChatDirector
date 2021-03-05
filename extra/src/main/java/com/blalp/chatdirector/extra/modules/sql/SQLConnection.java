package com.blalp.chatdirector.extra.modules.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ILoadable;

public class SQLConnection implements ILoadable {
    String connectionString;
    Connection connection;
    List<String> tables = new ArrayList<>();

    public SQLConnection(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public boolean load() {
        try {
            if (ChatDirector.getInstance().isDebug()) {
                ChatDirector.getLogger().log(Level.INFO, "Loading " + this);
            }
            connection = DriverManager.getConnection(connectionString);
            if (ChatDirector.getInstance().isDebug()) {
                ChatDirector.getLogger().log(Level.INFO, "Loaded " + this);
            }
        } catch (SQLException e) {
            if (ChatDirector.getInstance().isTesting()) {
                System.err.println(
                        "connection " + connectionString + " failed, but we are testing so its to be expected.");
                return true;
            } else {
                System.err.println("connection " + connectionString + " failed.");
                e.printStackTrace();
                return false;
            }
        }
        for (String table : tables) {
            try {
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table
                        + " (`name` varchar(255) NOT NULL,`key` varchar(255) NOT NULL, `value` varchar(255) NOT NULL, PRIMARY KEY (`key`, `name`));")
                        .execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        ChatDirector.getLogger().log(Level.INFO, "Unloading " + this);
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to unload " + connectionString);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void addTable(String table) {
        tables.add(table);
    }

    public void addTables(Iterable<String> list) {
        for (String table : list) {
            addTable(table);
        }
    }
}
