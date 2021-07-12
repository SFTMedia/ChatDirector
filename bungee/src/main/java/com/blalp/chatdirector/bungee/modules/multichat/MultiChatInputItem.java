package com.blalp.chatdirector.bungee.modules.multichat;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ItemDaemon;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class MultiChatInputItem extends PassItem {
    boolean global, staff, broadcast;

    public MultiChatInputItem() {
        ((ItemDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(MultiChatInputDaemon.class)).addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(global, staff, broadcast);
    }
}
