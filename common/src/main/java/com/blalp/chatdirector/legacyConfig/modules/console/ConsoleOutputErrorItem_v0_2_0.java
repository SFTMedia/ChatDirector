package com.blalp.chatdirector.legacyConfig.modules.console;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

public class ConsoleOutputErrorItem_v0_2_0 implements ILegacyItem {

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
        return "console-output-error";
    }
    
}
