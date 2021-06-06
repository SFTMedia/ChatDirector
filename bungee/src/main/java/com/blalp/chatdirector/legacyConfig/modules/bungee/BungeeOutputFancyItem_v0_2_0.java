package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.blalp.chatdirector.minecraft.model.FancyMessage;

import lombok.Data;

@Data
public class BungeeOutputFancyItem_v0_2_0 implements ILegacyItem {
    FancyMessage fancyMessage;
    String permission;
    boolean sendToCurrentServer = true;
    String player = null;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "bungee-output-fancy";
    }
    
}
