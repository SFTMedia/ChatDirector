package com.blalp.chatdirector.legacyConfig.modules.logic;

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
@JsonDeserialize(using = SplitItemDeserializer_v0_2_5.class)
@JsonSerialize(using = SplitItemSerializer_v0_2_5.class)
public class SplitItem_v0_2_5 implements ILegacyItem {
    Map<String,LegacyChain> chains = new HashMap<String,LegacyChain>();

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
        return "split";
    }
    
}
