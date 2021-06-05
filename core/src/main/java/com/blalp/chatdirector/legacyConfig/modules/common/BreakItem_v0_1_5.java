package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class BreakItem_v0_1_5 implements ILegacyItem {
    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version();
    }

    @Override
    public String name() {
        return "break";
    }

}
