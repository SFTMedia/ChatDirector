package com.blalp.chatdirector.modules.cache;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class CacheGetItem extends CacheItem {

    public CacheGetItem(String key) {
        super(key);
    }

    @Override
    public Context process(Context context) {
        if(CacheStore.containsKey(ChatDirector.format(key, context))){
            return new Context(CacheStore.getValue(ChatDirector.format(key, context)));
        }
        return new Context();
    }
    
}
