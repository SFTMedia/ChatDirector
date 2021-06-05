package com.blalp.chatdirector.extra.modules.luckperms;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class LuckPermsModule implements IModule {

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
        return Arrays.asList("luckperms-context", "luckperms-set", "luckperms-unset", "luckperms-has");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
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