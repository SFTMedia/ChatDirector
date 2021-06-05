package com.blalp.chatdirector.legacyConfig.modules.context;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class ContextSetItem_v0_2_0 implements ILegacyItem {
    String key;
    String value = "%CURRENT%";
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
        return "context-set";
    }
    
}
