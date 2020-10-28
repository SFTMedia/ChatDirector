package com.blalp.chatdirector.modules.sponge;

import com.blalp.chatdirector.modules.common.PassItem;

public class SpongeCommandItem extends PassItem {
    String command;
    String permission;
    public SpongeCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
        new SpongeCommand(name, this);
    }
}
