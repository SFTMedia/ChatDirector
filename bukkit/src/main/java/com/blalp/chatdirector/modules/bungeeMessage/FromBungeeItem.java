package com.blalp.chatdirector.modules.bungeeMessage;

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
    public String channel;

    public FromBungeeItem() {
        if (FromBungeeDaemon.instance == null) {
            FromBungeeDaemon.instance = new FromBungeeDaemon();
        }
        FromBungeeDaemon.instance.addItem(this);

    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel);
    }
}
