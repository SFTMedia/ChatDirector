package com.blalp.chatdirector.extra.modules.mqtt;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.IDaemon;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class MQTTInputItem extends MQTTItem {
    String topic;

    public MQTTInputItem() {
        ((IDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(MQTTInputDaemon.class)).addItem(this);
    }
}
