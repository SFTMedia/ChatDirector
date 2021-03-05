package com.blalp.chatdirector.bukkit.modules.placeholderapi;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

/**
 * PlaceholderapiModule
 */
public class PlaceholderapiModule implements IModule {

    @Override
    public boolean load() {
        try {
            Class.forName("org.bukkit.Bukkit");
        } catch (ClassNotFoundException e) {
            return true;
        }
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
        return Arrays.asList("placeholderapi-resolve");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
            case "placeholderapi-resolve":
                return PlaceholderapiResolveItem.class;
            default:
                return null;
        }
    } 
}