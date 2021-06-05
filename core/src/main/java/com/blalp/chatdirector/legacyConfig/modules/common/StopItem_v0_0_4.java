package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class StopItem_v0_0_4 implements ILegacyItem {

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return Arrays.asList(new HaltItem_v0_1_5());
    }

    @Override
    public Version getNextUpdateVersion() {
        return new Version(0,1,5);
    }
    
}
