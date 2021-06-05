package com.blalp.chatdirector.legacyConfig.modules.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class CommonLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        List<String> output = new ArrayList<>();
        if(version.compareTo(new PassItem_v0_1_5().getNextUpdateVersion())<0) {
            output.add("pass");
        }
        output.addAll(Arrays.asList("pass", "stop", "halt", "break", "echo", "reload"));
        return output;
    }

    @Override
    public Class<? extends IItem> getItemClass(String type, Version version) {
        switch (type) {
            case "pass":
                
                break;
        
            default:
                break;
        }
        return null;
    }
}
