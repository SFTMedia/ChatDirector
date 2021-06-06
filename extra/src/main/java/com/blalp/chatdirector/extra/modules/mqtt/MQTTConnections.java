package com.blalp.chatdirector.extra.modules.mqtt;

import java.util.HashMap;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.ILoadable;

public class MQTTConnections extends HashMap<String, MQTTConnection> implements ILoadable {

    boolean loaded = false;

    @Override
    public boolean load() {
        if (!loaded) {
            loaded = true;
            for (MQTTConnection connection : this.values()) {
                if (!connection.load()) {
                    ChatDirector.getLogger().log(Level.SEVERE, connection + " failed to load.");
                    return false;
                }
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

}
