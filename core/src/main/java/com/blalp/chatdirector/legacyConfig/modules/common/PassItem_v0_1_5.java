package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class PassItem_v0_1_5 implements ILegacyItem {

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version(0, 1, 8);
    }

    @Override
    public String name() {
        return "pass";
    }

}
