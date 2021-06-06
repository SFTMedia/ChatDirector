package com.blalp.chatdirector.legacyConfig.modules.context;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class ContextLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("get-context", "set-context", "remove-context", "resolve-context");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "get-context":
            return ContextGetItem_v0_2_0.class;
        case "set-context":
            return ContextSetItem_v0_2_0.class;
        case "remove-context":
            return ContextRemoveItem_v0_2_0.class;
        case "resolve-context":
            return ContextResolveItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
