package com.blalp.chatdirector.internalModules.permissions;

// Simple class that implements IPermission with no perms. Used before the permission plugin is loaded
public class DenyPermission implements IPermission {

    @Override
    public boolean hasPermission(String playerName, String permission) {
        return false;
    }

    @Override
    public String getPrefix(String playerName) {
        return "";
    }

    @Override
    public String getSuffix(String playerName) {
        return "";
    }

    @Override
    public String getGroup(String playerName) {
        return "";
    }
    
}
