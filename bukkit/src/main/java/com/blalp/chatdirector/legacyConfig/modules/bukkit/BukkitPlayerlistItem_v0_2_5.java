package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BukkitPlayerlistItem_v0_2_5 implements ILegacyItem {
    String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    String formatNoPlayers = "**No online players**";
    String formatPlayer = "%PLAYER_NAME%";

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
        return "bukkit-playerlist";
    }
}
