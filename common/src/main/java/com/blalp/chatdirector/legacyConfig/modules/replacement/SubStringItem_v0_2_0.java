package com.blalp.chatdirector.legacyConfig.modules.replacement;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SubStringItem_v0_2_0 implements ILegacyItem {
    int start = 0;
    int end = -1;

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
        return "sub-string";
    }

}
