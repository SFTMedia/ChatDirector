package com.blalp.chatdirector.legacyConfig.modules.luckperms;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class LuckPermsUnsetItem_v0_2_0 implements ILegacyItem {
    String permission;
    boolean value = true;

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
        return "luckperms-unset";
    }
    
}
