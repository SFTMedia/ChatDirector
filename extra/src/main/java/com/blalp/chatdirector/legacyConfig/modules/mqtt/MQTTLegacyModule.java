package com.blalp.chatdirector.legacyConfig.modules.mqtt;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class MQTTLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("mqtt-input", "mqtt-output");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "mqtt-input":
            return MQTTInputItem_v0_2_0.class;
        case "mqtt-output":
            return MQTTOutputItem_v0_2_0.class;
        }
        return null;
    }

}
