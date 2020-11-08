package com.blalp.chatdirector.modules.luckperms;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.blalp.chatdirector.configuration.Configuration;
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
        LinkedHashMap<String, Object> configMap = (LinkedHashMap<String, Object>)config;
        switch (type) {
            case "luckperms-context":
                return new LuckPermsContextItem();
            case "luckperms-set":
                LuckPermsSetItem luckPermsSetItem = new LuckPermsSetItem((String)configMap.get("permission"));
                if(configMap.containsKey("value")) {
                    luckPermsSetItem.value=(boolean)configMap.containsKey("value");
                }
                return luckPermsSetItem;
            case "luckperms-unset":
                LuckPermsUnsetItem unsetItem = new LuckPermsUnsetItem((String)configMap.get("permission"));
                if(configMap.containsKey("value")) {
                    unsetItem.value=(boolean)configMap.containsKey("value");
                }
                return unsetItem;
            case "luckperms-has":
                LuckPermsHasItem luckPermsHasItem = new LuckPermsHasItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes-chain")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no-chain")),(String)configMap.get("permission"));
                if(configMap.containsKey("value")) {
                    luckPermsHasItem.value=(boolean)configMap.containsKey("value");
                }
                return luckPermsHasItem;
            default:
                return null;
        }
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"luckperms-context","luckperms-set","luckperms-unset","luckperms-has"};
    }

}