package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class BukkitCommandItem extends PassItem {
    String command;
    String permission;
    public BukkitCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
        new BukkitCommand(name, this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command,permission);
    }
}
