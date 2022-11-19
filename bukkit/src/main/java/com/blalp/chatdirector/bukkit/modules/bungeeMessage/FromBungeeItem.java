package com.blalp.chatdirector.bukkit.modules.bungeeMessage;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ItemDaemon;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class FromBungeeItem extends PassItem {
    String channel;

    public FromBungeeItem() {
        ((ItemDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(FromBungeeDaemon.class)).addItem(this);

    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel);
    }
}
