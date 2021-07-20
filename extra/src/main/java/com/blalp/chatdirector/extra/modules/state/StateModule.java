package com.blalp.chatdirector.extra.modules.state;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class StateModule implements IModule {

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
        return Arrays.asList("serialize-state","deserialize-state");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "serialize-state":
            return SerializeStateItem.class;
        case "deserialize-state":
            return DeserializeStateItem.class;
        default:
            return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return null;
    }
    
}
