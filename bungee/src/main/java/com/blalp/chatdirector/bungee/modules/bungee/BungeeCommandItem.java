package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class BungeeCommandItem extends PassItem {
    String command;
    String permission;

    public BungeeCommandItem() {
        ((BungeeCommandDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(BungeeCommandDaemon.class))
                .addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command, permission);
    }
}
