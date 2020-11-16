package com.blalp.chatdirector.modules.cache;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.logic.ConditionalItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class CacheIfItem extends ConditionalItem {
    String key;
    public CacheIfItem(Chain trueChain, Chain falseChain,String key){
        super(trueChain, falseChain);
        this.key=key;
    }
    @Override
    public boolean test(Context context) {
        return CacheStore.containsKey(ChatDirector.format(key, context));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(key)&&super.isValid();
    }
    
}
