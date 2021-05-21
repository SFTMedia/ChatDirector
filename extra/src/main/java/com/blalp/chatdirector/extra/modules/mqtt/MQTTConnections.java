package com.blalp.chatdirector.extra.modules.mqtt;

import java.util.HashMap;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

public class MQTTConnections extends HashMap<String, MQTTConnection> implements IDaemon {

    @Override
    public boolean load() {
        for (MQTTConnection connection : this.values()) {
            if (!connection.load()) {
                ChatDirector.getLogger().log(Level.SEVERE, connection + " failed to load.");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (MQTTConnection connection : this.values()) {
            if (!connection.unload()) {
                ChatDirector.getLogger().log(Level.SEVERE, connection + " failed to load.");
                return false;
            }
        }
        return true;
    }

    @Override
    public void addItem(IItem item) {
        try {
            throw new Exception("Not implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
