package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.modules.common.PassItem;

public class BukkitCommandInputItem extends PassItem {
    public String commandName;
    public String[] args=new String[]{};
    public String permission;
    public String format="/%COMMAND% %ARGS%";
    public BukkitCommandInputItem(String commandName,String permission){
        this.commandName=commandName;
        this.permission=permission;
    }
}
