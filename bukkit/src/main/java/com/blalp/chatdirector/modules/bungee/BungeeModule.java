package com.blalp.chatdirector.modules.bungee;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"bungee-to","bungee-from"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "bungee-to":
                return new ToBungeeItem((String)((LinkedHashMap<String,Object>)config).get("channel"));
            case "bungee-from":
                if(FromBungeeDaemon.instance==null){
                    FromBungeeDaemon.instance=new FromBungeeDaemon();
                    FromBungeeDaemon.instance.load();
                }
                FromBungeeItem item = new FromBungeeItem((String)((LinkedHashMap<String,Object>)config).get("channel"));
                FromBungeeDaemon.instance.addItem(item);
                return item;
        }
        return null;
    }

    @Override
    public void load() {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.instance.unload();
        }
    }
    
}
