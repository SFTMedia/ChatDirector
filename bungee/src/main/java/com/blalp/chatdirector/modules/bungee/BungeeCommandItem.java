package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;

public class BungeeCommandItem extends PassItem {
    String command;
    String permission;
    public BungeeCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
    }
}
