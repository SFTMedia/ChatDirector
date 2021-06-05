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
            if (version.compareTo(new BukkitInputItem_v0_2_0().nextUpdateVersion()) > 0) {
                return BukkitInputItem_v0_2_5.class;
            } else {
                return BukkitInputItem_v0_2_0.class;
            }
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

    @Override
    public String getItemName(Class<? extends ILegacyItem> itemClass) {
        if (itemClass.equals(BukkitInputItem_v0_2_5.class) || itemClass.equals(BukkitInputItem_v0_2_0.class)) {
            return "bukkit-input";
        } else if (itemClass.equals(BukkitOutputItem_v0_2_5.class)) {
            return "bukkit-output";
        } else if (itemClass.equals(BukkitPlayerlistItem_v0_2_5.class)) {
            return "bukkit-playerlist";
        } else if (itemClass.equals(BukkitCommandItem_v0_2_5.class)) {
            return "bukkit-command";
        } else {
            return null;
        }
    }

}
