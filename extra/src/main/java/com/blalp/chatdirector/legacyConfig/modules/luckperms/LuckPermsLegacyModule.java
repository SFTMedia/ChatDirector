package com.blalp.chatdirector.legacyConfig.modules.luckperms;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class LuckPermsLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("luckperms-context", "luckperms-set", "luckperms-unset", "luckperms-has");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "luckperms-context":
            return LuckPermsContextItem_v0_2_0.class;
        case "luckperms-set":
            return LuckPermsSetItem_v0_2_0.class;
        case "luckperms-unset":
            return LuckPermsUnsetItem_v0_2_0.class;
        case "luckperms-has":
            return LuckPermsHasItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
