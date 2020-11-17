package com.blalp.chatdirector.modules.bungee;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

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
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "bungee-to":
                return ToBungeeItem.class;
            case "bungee-from":
                return FromBungeeItem.class;
            default:
                return null;
        }
    }

}
