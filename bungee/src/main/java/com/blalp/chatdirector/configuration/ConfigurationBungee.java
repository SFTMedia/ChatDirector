package com.blalp.chatdirector.configuration;

import java.util.Map.Entry;

import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
import com.blalp.chatdirector.modules.multichat.MultiChatModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationBungee extends Configuration {

    public ConfigurationBungee(String fileName) {
        super(fileName);
    }
    @Override
    public IModule loadModule(ObjectMapper om, Entry<String, JsonNode> module) {
        switch (module.getKey()) {
            case "bungee":
                return new BungeeModule();
            case "multichat":
                return new MultiChatModule();
            default:
                return super.loadModule(om, module);
        }
    }
}