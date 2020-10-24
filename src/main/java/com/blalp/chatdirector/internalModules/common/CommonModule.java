package com.blalp.chatdirector.internalModules.common;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class CommonModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"pass-item","null-item"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "pass-item":
                return new PassItem();
            case "null-item":
                return new NullItem();
            default:
                return null;
        }
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }
    
}
