package com.blalp.chatdirector.modules.context;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ContextModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"get-context","set-context","remove-context","resolve-context"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String, String> configMap = (LinkedHashMap<String, String>)config;
        switch (type){
            case "get-context":
                return new ContextGetItem(configMap.get("context"));
            case "resolve-context":
                return new ContextResolveItem();
            case "set-context":
                ContextSetItem item = new ContextSetItem(configMap.get("context"));
                if(configMap.containsKey("value")){
                    item.value=configMap.get("value");
                }
                return item;
            case "remove-context":
                return new ContextRemoveItem(configMap.get("context"));
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
