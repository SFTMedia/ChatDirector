package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class BungeeCommandItem extends PassItem {
    String command;
    String permission;
    public BungeeCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
        new BungeeCommand(name, this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command,permission);
    }
}
