package com.blalp.chatdirector.legacyConfig.modules.sponge;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SpongeLegacyModule implements ILegacyModule {@Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("sponge-output", "sponge-input", "sponge-playerlist", "sponge-command");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "sponge-command":
            return SpongeCommandItem_v0_2_0.class;
        case "sponge-output":
            return SpongeOutputItem_v0_2_0.class;
        case "sponge-input":
            return SpongeInputItem_v0_2_0.class;
        case "sponge-playerlist":
            return SpongePlayerlistItem_v0_2_0.class;
        default:
            return null;
        }
    }
    
}
