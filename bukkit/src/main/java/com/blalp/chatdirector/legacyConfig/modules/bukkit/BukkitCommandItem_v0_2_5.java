package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BukkitCommandItem_v0_2_5 implements ILegacyItem {
    String command;
    String permission;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version();
    }
    
}
