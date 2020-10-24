package com.blalp.chatdirector.modules.luckperms;

import com.blalp.chatdirector.model.PermissionItem;

import net.luckperms.api.LuckPermsProvider;

public class LuckPermsContextItem extends PermissionItem {
    
    @Override
    public String getPrefix(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData().getPrefix();
    }

    @Override
    public String getSuffix(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData().getSuffix();
    }

    @Override
    public String getGroup(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData().getPrimaryGroup();
    }

}
