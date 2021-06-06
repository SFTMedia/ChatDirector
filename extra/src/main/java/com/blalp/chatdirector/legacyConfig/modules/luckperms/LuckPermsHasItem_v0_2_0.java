package com.blalp.chatdirector.legacyConfig.modules.luckperms;

import java.util.List;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonDeserialize(using = LuckPermsHasItemDeserializer_v0_2_0.class)
public class LuckPermsHasItem_v0_2_0 implements ILegacyItem {
    String permission;

    LegacyChain yesChain;
    LegacyChain noChain;
    String source = "%CURRENT%";

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
        return "luckperms-has";
    }

}
