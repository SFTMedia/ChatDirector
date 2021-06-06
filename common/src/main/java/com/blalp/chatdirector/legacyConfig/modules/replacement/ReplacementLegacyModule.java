package com.blalp.chatdirector.legacyConfig.modules.replacement;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class ReplacementLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("regex", "remove-colors", "resolve-colors", "sub-string", "to-lower", "to-upper",
                "to-word");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "regex":
            return RegexItem_v0_2_0.class;
        case "remove-colors":
            return RemoveColorsItem_v0_2_0.class;
        case "resolve-colors":
            return ResolveColorsItem_v0_2_0.class;
        case "sub-string":
            return SubStringItem_v0_2_0.class;
        case "to-lower":
            return ToLowerItem_v0_2_0.class;
        case "to-upper":
            return ToUpperItem_v0_2_0.class;
        case "to-word":
            return ToWordItem_v0_2_0.class;
        default:
            return null;
        }
    }
    
}
