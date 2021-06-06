package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.blalp.chatdirector.legacyConfig.minecraft.model.FancyMessage_v0_2_0;

import lombok.Data;

@Data
public class BungeeOutputFancyItem_v0_2_0 implements ILegacyItem {
    FancyMessage_v0_2_0 fancyMessage;
    String permission;
    boolean sendToCurrentServer = true;
    String player = null;

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
        return "bungee-output-fancy";
    }

}
