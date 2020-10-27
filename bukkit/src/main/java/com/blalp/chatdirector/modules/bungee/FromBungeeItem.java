package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.internalModules.common.PassItem;

public class FromBungeeItem extends PassItem {
    public String channel;
    public FromBungeeItem(String channel){
        this.channel=channel;
    }
}
