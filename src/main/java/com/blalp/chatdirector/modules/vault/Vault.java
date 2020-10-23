package com.blalp.chatdirector.modules.vault;

import com.blalp.chatdirector.internalModules.permissions.IPermission;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

import org.apache.commons.lang.NotImplementedException;

public class Vault extends Module implements IPermission {

    @Override
    public String[] getItemNames() {
        return new String[]{};
    }

    @Override
    public IItem createItem(String type, Object config) {
        return null;
    }

    @Override
    public boolean hasPermission(String playerName, String permission) {
        throw new NotImplementedException();
    }

    @Override
    public String getPrefix(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public String getSuffix(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public String getGroup(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public void load() {
        throw new NotImplementedException();
    }

    @Override
    public void unload() {
        throw new NotImplementedException();
    }    

    
}