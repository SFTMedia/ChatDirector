package com.blalp.chatdirector.common.modules.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

public class CacheStore implements IDaemon {
    private ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();

    public String getValue(String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return "";
    }

    public void setValue(String key, String value) {
        data.put(key, value);
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public void shred() {
        data = new ConcurrentHashMap<String, String>();
    }

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        shred();
        return true;
    }

    @Override
    public void addItem(IItem item) {
        // N/A
    }
}
