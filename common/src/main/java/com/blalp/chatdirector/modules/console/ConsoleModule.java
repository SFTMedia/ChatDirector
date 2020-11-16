package com.blalp.chatdirector.modules.console;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleModule implements IModule {

    @Override
    public void load() {
        // Nothing needed
    }

    @Override
    public void unload() {
        // Nothing needed
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList( "console-output-error", "console-output");
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "console-output-error":
                return new ConsoleOutputErrorItem();
            case "console-output":
                return new ConsoleOutputItem();
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
}
