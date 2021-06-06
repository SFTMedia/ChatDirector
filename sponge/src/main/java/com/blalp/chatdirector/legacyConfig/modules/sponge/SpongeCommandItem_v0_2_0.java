package com.blalp.chatdirector.legacyConfig.modules.sponge;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SpongeCommandItem_v0_2_0 implements ILegacyItem {
    String command;
    String permission;
    boolean args = false;
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
        return "sponge-command";
    }

}
