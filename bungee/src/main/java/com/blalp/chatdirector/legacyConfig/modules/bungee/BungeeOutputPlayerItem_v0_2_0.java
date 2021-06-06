package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BungeeOutputPlayerItem_v0_2_0 implements ILegacyItem {
    String player;
    String permission;

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
        return "bungee-output-player";
    }
    
}
