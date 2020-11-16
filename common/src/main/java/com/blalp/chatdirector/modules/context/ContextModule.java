package com.blalp.chatdirector.modules.context;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContextModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("get-context", "set-context", "remove-context", "resolve-context");
    }

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
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "get-context":
                return om.convertValue(config, ContextGetItem.class);
            case "resolve-context":
                return om.convertValue(config, ContextResolveItem.class);
            case "set-context":
                return om.convertValue(config, ContextSetItem.class);
            case "remove-context":
                return om.convertValue(config, ContextRemoveItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
