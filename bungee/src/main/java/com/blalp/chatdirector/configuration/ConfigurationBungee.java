package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
import com.blalp.chatdirector.modules.multichat.MultiChatModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationBungee extends Configuration {

    public ConfigurationBungee(String fileName) {
        super(fileName);
    }
    @Override
    public IItem loadModule(ObjectMapper om, String moduleKey,JsonNode moduleValue) {
        switch (moduleKey) {
            case "bungee":
                return new BungeeModule();
            case "multichat":
                return new MultiChatModule();
            default:
                return super.loadModule(om, moduleKey,moduleValue);
        }
    }
}