package com.blalp.chatdirector.legacyConfig.modules.placeholderapi;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class PlaceholderAPIResolveItem_v0_2_5 implements ILegacyItem {

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version getNextUpdateVersion() {
        return new Version();
    }
    
}
