package com.blalp.chatdirector.modules.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class CacheModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"cache-get","cache-set","cache-if"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String, Object> configMap = (LinkedHashMap<String, Object>) config;
        switch (type) {
            case "cache-get":
                return new CacheGetItem((String)configMap.get("key"));
            case "cache-set":
                return new CacheSetItem((String)configMap.get("key"), (String)configMap.get("value"));
            case "cache-if":
                return new CacheIfItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes-chain")), Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no-chain")), (String)configMap.get("key"));
            default:
                return null;
        }
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {
        CacheStore.shred();
    }
    
}
