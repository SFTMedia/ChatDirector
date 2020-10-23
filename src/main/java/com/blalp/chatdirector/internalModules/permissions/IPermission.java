package com.blalp.chatdirector.internalModules.permissions;

public interface IPermission {
    public boolean hasPermission(String playerName,String permission);
    public String getPrefix(String playerName);
    public String getSuffix(String playerName);
    public String getGroup(String playerName);
}