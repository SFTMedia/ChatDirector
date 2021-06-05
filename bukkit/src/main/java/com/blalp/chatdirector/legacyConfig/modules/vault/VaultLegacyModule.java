package com.blalp.chatdirector.legacyConfig.modules.vault;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class VaultLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("vault-context");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "vault-context":
            return VaultContextItem_v0_2_5.class;
        default:
            return null;
        }
    }

    @Override
    public String getItemName(Class<? extends ILegacyItem> itemClass) {
        if(itemClass.equals(VaultContextItem_v0_2_5.class)){
            return "vault-context";
        } else {
            return null;
        }
    }
}
