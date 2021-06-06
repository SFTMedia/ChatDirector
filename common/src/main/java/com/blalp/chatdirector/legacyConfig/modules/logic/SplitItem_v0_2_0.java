package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonDeserialize(using = SplitItemDeserializer_v0_2_0.class)
@JsonSerialize(using = SplitItemSerializer_v0_2_0.class)
public class SplitItem_v0_2_0 implements ILegacyItem {
    Map<String, LegacyChain> chains = new HashMap<String, LegacyChain>();

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        SplitItem_v0_2_5 output = new SplitItem_v0_2_5();
        output.chains = chains;
        return Arrays.asList(output);
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version(0, 2, 5);
    }

    @Override
    public String name() {
        return "split";
    }

}
