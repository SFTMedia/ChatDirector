package com.blalp.chatdirector.modules.bungee;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.model.IItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BungeeModule implements IModule {

    @Override
    public void load() {
        if (FromBungeeDaemon.instance != null) {
            FromBungeeDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if (FromBungeeDaemon.instance != null) {
            FromBungeeDaemon.instance.unload();
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("bungee-to", "bungee-from");
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "bungee-to":
                return om.convertValue(config,ToBungeeItem.class);
            case "bungee-from":
                if (FromBungeeDaemon.instance == null) {
                    FromBungeeDaemon.instance = new FromBungeeDaemon();
                    FromBungeeDaemon.instance.load();
                }
                FromBungeeItem item = om.convertValue(config,FromBungeeItem.class);
                FromBungeeDaemon.instance.addItem(item,chain);
                return item;
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
