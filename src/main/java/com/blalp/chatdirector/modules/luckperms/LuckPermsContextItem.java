package com.blalp.chatdirector.modules.luckperms;

import java.util.Map;

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

    @Override
    public String process(String string, Map<String, String> context) {
        super.process(string,context);
        this.context.put("SERVERNAME", LuckPermsProvider.get().getServerName());
        return string;
    }

}
