package com.blalp.chatdirector.legacyConfig.modules.console;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class ConsoleLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("console-output-error", "console-output");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "console-output":
            return ConsoleOutputItem_v0_2_0.class;
        case "console-output-error":
            return ConsoleOutputErrorItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
