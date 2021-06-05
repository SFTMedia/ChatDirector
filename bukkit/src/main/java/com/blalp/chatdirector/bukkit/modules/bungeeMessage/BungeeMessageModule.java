package com.blalp.chatdirector.bukkit.modules.bungeeMessage;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class BungeeMessageModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList();
        // return Arrays.asList("bungee-to", "bungee-from");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        return null;
        /*
         * switch (type) { case "bungee-to": return ToBungeeItem.class; case
         * "bungee-from": return FromBungeeItem.class; default: return null; }
         */
    }

}
