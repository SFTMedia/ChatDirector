package com.blalp.chatdirector.legacyConfig.modules.replacement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonDeserialize(using = RegexItemDeserializer_v0_2_0.class)
public class RegexItem_v0_2_0 implements ILegacyItem {
    Map<String, String> pairs = new LinkedHashMap<String, String>();

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
        return "regex";
    }

}
