package com.blalp.chatdirector.modules.common;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("pass", "stop", "halt", "break", "echo", "reload");
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "stop":
            case "halt":
                return om.convertValue(config, HaltItem.class);
            case "break":
                return om.convertValue(config, BreakItem.class);
            case "echo":
                return new EchoItem(config.asText());
            case "reload":
                return om.convertValue(config, ReloadItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
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
    
}
