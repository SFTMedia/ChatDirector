package com.blalp.chatdirector.modules.multichat;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper=false)
public class MultiChatInputItem extends PassItem {
    public boolean global, staff, broadcast;

    public MultiChatInputItem() {
        if (MultiChatInputItemDaemon.instance == null) {
            MultiChatInputItemDaemon.instance = new MultiChatInputItemDaemon();
        }
        MultiChatInputItemDaemon.instance.addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(global, staff, broadcast);
    }
}
