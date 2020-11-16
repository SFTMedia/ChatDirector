package com.blalp.chatdirector.modules.luckperms;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "luckperms-context":
                return om.convertValue(config, LuckPermsContextItem.class);
            case "luckperms-set":
                return om.convertValue(config, LuckPermsSetItem.class);
            case "luckperms-unset":
                return om.convertValue(config, LuckPermsUnsetItem.class);
            case "luckperms-has":
                return new LuckPermsHasItem(ChatDirector.loadChain(om,config.get("yes-chain")),ChatDirector.loadChain(om,config.get("no-chain")),config.get("permission").asText());
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}