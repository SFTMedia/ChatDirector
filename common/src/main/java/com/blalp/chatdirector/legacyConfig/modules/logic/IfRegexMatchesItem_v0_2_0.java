package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.util.List;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class IfRegexMatchesItem_v0_2_0 implements ILegacyItem {  

    LegacyChain yesChain;
    LegacyChain noChain;
    String source = "%CURRENT%"; 
    String match;
    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }
    @Override
    public Version nextUpdateVersion() {
        return null;
    }
    @Override
    public String name() {
        return "if-regex-match";
    }
}
