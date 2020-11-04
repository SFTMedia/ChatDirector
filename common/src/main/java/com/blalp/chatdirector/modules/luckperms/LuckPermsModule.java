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
                return new LuckPermsSetItem((String)configMap.get("permission"));
            case "luckperms-unset":
                return new LuckPermsUnsetItem((String)configMap.get("permission"));
            case "luckperms-has":
                return new LuckPermsHasItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes-chain")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no-chain")),(String)configMap.get("permission"));
            default:
                return null;
        }
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"luckperms-context","luckperms-set","luckperms-unset","luckperms-has"};
    }

}