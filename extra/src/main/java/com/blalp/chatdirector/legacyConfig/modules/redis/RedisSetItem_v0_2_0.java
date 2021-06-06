package com.blalp.chatdirector.legacyConfig.modules.redis;

import lombok.Data;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

@Data
public class RedisSetItem_v0_2_0 implements ILegacyItem {
    String key, connection;
    String value;
    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Version nextUpdateVersion() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "redis-set";
    }

    
}
