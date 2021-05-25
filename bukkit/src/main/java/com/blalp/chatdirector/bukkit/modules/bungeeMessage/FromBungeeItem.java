package com.blalp.chatdirector.bukkit.modules.bungeeMessage;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class FromBungeeItem extends PassItem {
    String channel;

    public FromBungeeItem() {
        ChatDirector.getConfigStaging().getOrCreateDaemon(FromBungeeDaemon.class).addItem(this);

    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel);
    }
}
