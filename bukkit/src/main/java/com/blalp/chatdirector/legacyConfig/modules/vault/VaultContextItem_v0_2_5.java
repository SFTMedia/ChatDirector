package com.blalp.chatdirector.legacyConfig.modules.vault;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class VaultContextItem_v0_2_5 implements ILegacyItem {

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
        return "vault-context";
    }
}