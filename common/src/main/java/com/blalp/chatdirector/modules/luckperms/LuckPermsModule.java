package com.blalp.chatdirector.modules.luckperms;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

public class LuckPermsModule implements IModule {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("luckperms-context", "luckperms-set", "luckperms-unset", "luckperms-has");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "luckperms-context":
                return LuckPermsContextItem.class;
            case "luckperms-set":
                return LuckPermsSetItem.class;
            case "luckperms-unset":
                return LuckPermsUnsetItem.class;
            case "luckperms-has":
                return LuckPermsHasItem.class;
            default:
                return null;
        }
    }

}