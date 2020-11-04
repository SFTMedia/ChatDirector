package com.blalp.chatdirector.modules.cache;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class CacheGetItem extends CacheItem {

    public CacheGetItem(String key) {
        super(key);
    }

    @Override
    public String process(String string, Map<String, String> context) {
        if(CacheStore.containsKey(ChatDirector.format(key, context))){
            return CacheStore.getValue(ChatDirector.format(key, context));
        }
        this.context.put("CACHE_MISS", "TRUE");
        return "";
    }
    
}
