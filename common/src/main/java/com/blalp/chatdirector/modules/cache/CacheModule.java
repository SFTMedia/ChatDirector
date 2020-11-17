package com.blalp.chatdirector.modules.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

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
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<?> getItemClass(String type) {
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
