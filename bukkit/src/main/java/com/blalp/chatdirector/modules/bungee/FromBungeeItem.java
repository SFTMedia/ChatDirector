package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class FromBungeeItem extends PassItem {
    public String channel;
    public FromBungeeItem(String channel){
        this.channel=channel;
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel);
    }
}
