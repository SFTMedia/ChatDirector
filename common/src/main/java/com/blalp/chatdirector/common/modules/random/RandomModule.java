package com.blalp.chatdirector.common.modules.random;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class RandomModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("random");
    }

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
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
            case "random":
                return RandomItem.class;
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}
