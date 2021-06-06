package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BungeePlayerlistItem_v0_2_0 implements ILegacyItem {
    String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    String formatNoPlayers = "**No online players**";
    String formatPlayer = "%PLAYER_NAME%";
    String formatServer = "%SERVER_NAME%";
    boolean splitByServer = false;

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
        return "bungee-playerlist";
    }
    
}
