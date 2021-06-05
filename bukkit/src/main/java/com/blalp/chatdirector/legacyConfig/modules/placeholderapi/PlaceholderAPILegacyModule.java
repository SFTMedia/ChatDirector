package com.blalp.chatdirector.legacyConfig.modules.placeholderapi;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class PlaceholderAPILegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("placeholderapi-resolve");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "placeholderapi-resolve":
            return PlaceholderAPIResolveItem_v0_2_5.class;
        default:
            return null;
        }
    }    
}
