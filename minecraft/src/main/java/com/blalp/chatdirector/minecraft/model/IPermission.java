package com.blalp.chatdirector.abstractions.permission;

public interface IPermission {

    public String getPrefix(String playerName);

    public String getSuffix(String playerName);

    public String getGroup(String playerName);
}
