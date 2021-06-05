package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class BukkitLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("bukkit-input", "bukkit-output", "bukkit-playerlist", "bukkit-command");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "bukkit-input":
            return BukkitInputItem_v0_2_5.class;
        case "bukkit-output":
            return BukkitOutputItem_v0_2_5.class;
        case "bukkit-playerlist":
            return BukkitPlayerlistItem_v0_2_5.class;
        case "bukkit-command":
            return BukkitCommandItem_v0_2_5.class;
        default:
            return null;
        }
    }
    
}
