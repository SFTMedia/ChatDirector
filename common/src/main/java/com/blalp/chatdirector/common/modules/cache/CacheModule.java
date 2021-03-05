package com.blalp.chatdirector.common.modules.cache;

import java.util.Arrays;
import java.util.List;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

public class CacheModule implements IModule {

    public boolean load() {
        return true;
    }

    public boolean unload() {
        CacheStore.shred();
        return true;
    }

    public boolean isValid() {
        return true;
    }

    public List<String> getItemNames() {
        return Arrays.asList("cache-get", "cache-set", "cache-if");
    }

    public Context getContext(Object object) {
        return new Context();
    }

    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "cache-get":
            return CacheGetItem.class;
        case "cache-set":
            return CacheSetItem.class;
        case "cache-if":
            return CacheIfItem.class;
        default:
            return null;
        }
    }

}
