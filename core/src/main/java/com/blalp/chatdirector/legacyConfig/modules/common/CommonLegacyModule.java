package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class CommonLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        List<String> output = new ArrayList<>();
        output.addAll(Arrays.asList("pass", "stop", "halt", "break", "echo", "reload"));
        return output;
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
            case "pass":
                return PassItem_v0_1_5.class;
            case "stop":
                return StopItem_v0_0_4.class;
            case "halt":
                return HaltItem_v0_1_5.class;
            case "break":
                return BreakItem_v0_1_5.class;
            case "echo":
                return EchoItem_v0_1_5.class;
            case "reload":
                return ReloadItem_v0_1_5.class;
        }
        return null;
    }

    @Override
    public String getItemName(Class<? extends ILegacyItem> itemClass) {
        if(itemClass.equals(PassItem_v0_1_5.class)){
            return "pass";
        } else if (itemClass.equals(StopItem_v0_0_4.class)){
            return "stop";
        } else if (itemClass.equals(HaltItem_v0_1_5.class)){
            return "halt";
        } else if (itemClass.equals(BreakItem_v0_1_5.class)){
            return "break";
        } else if (itemClass.equals(EchoItem_v0_1_5.class)) {
            return "echo";
        } else if (itemClass.equals(ReloadItem_v0_1_5.class)) {
            return "reload";
        } else {
            return null;
        }
    }
}
