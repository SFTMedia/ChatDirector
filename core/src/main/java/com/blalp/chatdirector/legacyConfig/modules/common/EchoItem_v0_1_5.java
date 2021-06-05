package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@JsonDeserialize(using = EchoItemDeserializer_v0_1_5.class)
@JsonSerialize(using = EchoItemSerializer_v0_1_5.class)
@AllArgsConstructor
public class EchoItem_v0_1_5 implements ILegacyItem {
    String format;

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
        return "echo";
    }

}
