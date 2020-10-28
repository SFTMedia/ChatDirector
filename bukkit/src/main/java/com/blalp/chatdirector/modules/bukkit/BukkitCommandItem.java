package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.modules.common.PassItem;

public class BukkitCommandItem extends PassItem {
    String command;
    String permission;
    public BukkitCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
    }
}
