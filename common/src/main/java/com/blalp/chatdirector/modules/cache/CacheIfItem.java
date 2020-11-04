package com.blalp.chatdirector.modules.cache;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.logic.ConditionalItem;

public class CacheIfItem extends ConditionalItem {
    String key;
    public CacheIfItem(IItem nestedTrue, IItem nestedFalse,String key){
        super(nestedTrue, nestedFalse);
        this.key=key;
    }
    @Override
    public boolean test(String string, Map<String, String> context) {
        return CacheStore.containsKey(ChatDirector.format(key, context));
    }
    
}
