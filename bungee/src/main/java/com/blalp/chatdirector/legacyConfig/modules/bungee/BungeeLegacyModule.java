package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class BungeeLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("bungee-command", "bungee-playerlist", "bungee-output", "bungee-input",
                "bungee-output-player", "bungee-output-server", "bungee-output-fancy");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "bungee-playerlist":
            return BungeePlayerlistItem_v0_2_0.class;
        case "bungee-command":
            return BungeeCommandItem_v0_2_0.class;
        case "bungee-output":
            return BungeeOutputItem_v0_2_0.class;
        case "bungee-output-fancy":
            return BungeeOutputFancyItem_v0_2_0.class;
        case "bungee-output-player":
            return BungeeOutputPlayerItem_v0_2_0.class;
        case "bungee-output-server":
            return BungeeOutputServerItem_v0_2_0.class;
        case "bungee-input":
            if (version.compareTo(new BungeeInputItem_v0_2_0().nextUpdateVersion()) > 0) {
                return BungeeInputItem_v0_2_5.class;
            } else {
                return BungeeInputItem_v0_2_0.class;
            }
        default:
            return null;
        }
    }
    
}
