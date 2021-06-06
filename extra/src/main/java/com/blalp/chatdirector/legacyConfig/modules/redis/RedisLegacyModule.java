package com.blalp.chatdirector.legacyConfig.modules.redis;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class RedisLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("redis-set", "redis-get", "redis-if", "redis-remove");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "redis-set":
            return RedisSetItem_v0_2_0.class;
        case "redis-get":
            return RedisGetItem_v0_2_0.class;
        case "redis-if":
            return RedisIfItem_v0_2_0.class;
        case "redis-remove":
            return RedisRemoveItem_v0_2_0.class;
        }
        return null;
    }

}
