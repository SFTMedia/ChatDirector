package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"ToBungee","FromBungee","BungeeCommand"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "ToBungee":
                return new ToBungeeItem();
            case "FromBungee":
                if(FromBungeeDaemon.instance==null){
                    FromBungeeDaemon.instance=new FromBungeeDaemon();
                }
                FromBungeeItem item = new FromBungeeItem();
                FromBungeeDaemon.instance.addItem(item);
                return item;
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
