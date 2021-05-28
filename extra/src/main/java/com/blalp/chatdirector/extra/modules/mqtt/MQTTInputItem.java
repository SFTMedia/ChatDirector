package com.blalp.chatdirector.extra.modules.mqtt;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IDaemon;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class MQTTInputItem extends MQTTItem {
    String topic;

    public MQTTInputItem() {
        ((IDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(MQTTInputDaemon.class)).addItem(this);
    }
}
