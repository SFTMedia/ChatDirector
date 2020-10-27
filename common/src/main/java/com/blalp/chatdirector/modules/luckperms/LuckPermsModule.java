package com.blalp.chatdirector.modules.luckperms;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class LuckPermsModule extends Module {

    @Override
    public void load() {
        
    }

    @Override
    public void unload() {
        
    }

    @Override
    public void reload() {
        
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "luckperms-context":
                return new LuckPermsContextItem();
            default:
                return null;
        }
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"luckperms-context"};
    }

}