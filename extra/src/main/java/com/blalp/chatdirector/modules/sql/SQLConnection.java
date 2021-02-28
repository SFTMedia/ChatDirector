package com.blalp.chatdirector.modules.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ILoadable;

public class SQLConnection implements ILoadable {
    String connectionString;
    public Connection connection;

    public SQLConnection(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public boolean load() {
        ChatDirector.getLogger().log(Level.WARNING, "Loading " + this);
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.err.println("connection " + connectionString + " failed.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean unload() {
        ChatDirector.getLogger().log(Level.WARNING, "Unloading " + this);
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to unload " + connectionString);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
