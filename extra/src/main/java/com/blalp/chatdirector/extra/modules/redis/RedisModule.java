package com.blalp.chatdirector.extra.modules.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class RedisModule implements IModule {

    @Override
    public boolean load() {
        if (ChatDirector.getConfig().getModuleData() == null
                || ChatDirector.getConfig().getModuleData().get("redis") == null) {
            if (ChatDirector.getConfig().hasDaemon(RedisConnections.class)) {
                // Only spit out a warning if there were SQL items
                ChatDirector.getLogger().log(Level.WARNING, "Failed to load Redis module, no module_data");
            } else {
                // Of if debug mode is on
                // ChatDirector.getLogger().log(Level.INFO,"Failed to load Redis module, no
                // module_data. If you are not using Redis items, you can safely ignore this.");
            }
            return true;
        }
        RedisConnections connections = (RedisConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(RedisConnections.class);
        for (Entry<String, String> connection : ChatDirector.getConfig().getModuleData().get("redis").entrySet()) {
            connections.put(connection.getKey(), new RedisConnection(connection.getValue()));
        }
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("redis-set", "redis-get", "redis-if", "redis-remove");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "redis-set":
            return RedisSetItem.class;
        case "redis-get":
            return RedisGetItem.class;
        case "redis-if":
            return RedisIfItem.class;
        case "redis-remove":
            return RedisRemoveItem.class;
        }
        return null;
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}
