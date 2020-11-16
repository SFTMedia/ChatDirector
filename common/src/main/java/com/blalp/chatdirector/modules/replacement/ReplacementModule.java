package com.blalp.chatdirector.modules.replacement;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReplacementModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("regex", "remove-colors", "resolve-colors", "sub-string", "to-lower", "to-upper", "to-word");
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
            case "regex":
                return new RegexItem(config);
            case "remove-colors":
                return om.convertValue(config, RemoveColorsItem.class);
            case "resolve-colors":
                return om.convertValue(config, ResolveColorsItem.class);
            case "sub-string":
                return om.convertValue(config, SubStringItem.class);
            case "to-lower":
                return om.convertValue(config, ToLowerItem.class);
            case "to-upper":
                return om.convertValue(config, ToUpperItem.class);
            case "to-word":
                return om.convertValue(config, ToWordItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
