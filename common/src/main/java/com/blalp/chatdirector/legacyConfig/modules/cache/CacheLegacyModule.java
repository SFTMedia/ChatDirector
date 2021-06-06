package com.blalp.chatdirector.legacyConfig.modules.cache;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class CacheLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("cache-get", "cache-set", "cache-if");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "cache-get":
            return CacheGetItem_v0_2_0.class;
        case "cache-set":
            return CacheSetItem_v0_2_0.class;
        case "cache-if":
            return CacheIfItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
