package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"bungee-to","bungee-from","bungee-command"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "bungee-to":
                return new ToBungeeItem();
            case "bungee-from":
                if(FromBungeeDaemon.instance==null){
                    FromBungeeDaemon.instance=new FromBungeeDaemon();
                }
                FromBungeeItem item = new FromBungeeItem();
                FromBungeeDaemon.instance.addItem(item);
                return item;
            case "bungee-command":
                return new BungeeCommandItem();
        }
        return null;
    }

    @Override
    public void load() {
        
    }

    @Override
    public void unload() {
    }
    
}
