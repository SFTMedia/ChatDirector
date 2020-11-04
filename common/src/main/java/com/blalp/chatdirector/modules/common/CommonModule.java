package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class CommonModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"pass","stop","halt","break","echo","reload"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "pass":
                return new PassItem();
            case "stop":
            case "halt":
                return new HaltItem();
            case "break":
                return new BreakItem();
            case "echo":
                return new EchoItem((String)config);
            case "reload":
                return new ReloadItem();
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
