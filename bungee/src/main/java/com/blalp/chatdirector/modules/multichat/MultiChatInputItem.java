package com.blalp.chatdirector.modules.multichat;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class MultiChatInputItem extends PassItem {
    public boolean global,staff,broadcast;

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(global,staff,broadcast);
    }
}
