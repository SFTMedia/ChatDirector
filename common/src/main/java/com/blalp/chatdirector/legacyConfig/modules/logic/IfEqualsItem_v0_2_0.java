package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.util.List;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class IfEqualsItem_v0_2_0 implements ILegacyItem {

    Chain yesChain;
    Chain noChain;
    String source = "%CURRENT%";
    String equals;
    boolean ignoreCase = false;
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
        return "if-equals";
    }
    
}
