package com.blalp.chatdirector.legacyConfig.modules.mqtt;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class MQTTInputItem_v0_2_0 implements ILegacyItem {
    String topic;
    String connection;
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
        return "mqtt-input";
    }
    
}
