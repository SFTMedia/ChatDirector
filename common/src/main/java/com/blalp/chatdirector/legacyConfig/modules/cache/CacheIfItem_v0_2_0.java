package com.blalp.chatdirector.legacyConfig.modules.cache;

import java.util.List;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonDeserialize(using = CacheIfItemDeserializer_v0_2_0.class)
@JsonSerialize(using = CacheIfItemSerializer_v0_2_0.class)
public class CacheIfItem_v0_2_0 implements ILegacyItem {

    LegacyChain yesChain;
    LegacyChain noChain;
    String source = "%CURRENT%";
    String key;

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
        return "cache-if";
    }
}
