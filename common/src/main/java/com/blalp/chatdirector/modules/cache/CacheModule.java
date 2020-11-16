package com.blalp.chatdirector.modules.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CacheModule implements IModule {

    @Override
    public void load() {
    }

    @Override
    public void unload() {
        CacheStore.shred();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return new ArrayList<>(Arrays.asList("cache-get", "cache-set", "cache-if"));
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "cache-get":
                return  om.convertValue(config, CacheGetItem.class);
            case "cache-set":
                return om.convertValue(config, CacheSetItem.class);
            case "cache-if":
                return new CacheIfItem(ChatDirector.loadChain(om,config.get("yes-chain")),ChatDirector.loadChain(om,config.get("no-chain")),config.get("key").asText());
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
