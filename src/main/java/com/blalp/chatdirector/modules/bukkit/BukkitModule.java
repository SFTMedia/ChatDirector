package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class BukkitModule extends Module {

    @Override
    public void load() {
        new BukkitInputDaemon().load();
    }

    @Override
    public void unload() {
    }
    @Override
    public String[] getItemNames() {
        return new String[]{"BukkitInput","BukkitOutput","BukkitPlayerlist"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "BukkitInput":
                return new BukkitInputItem();
            case "BukkitOutput":
                return new BukkitOutputItem();
            case "BukkitPlayerlist":
                return new BukkitPlayerlistItem();
        }
        return null;
    }
    
}
