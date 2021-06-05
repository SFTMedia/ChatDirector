package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BukkitOutputItem_v0_2_5 implements ILegacyItem {
    String permission = null;

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
        return "bukkit-output";
    }    
}
