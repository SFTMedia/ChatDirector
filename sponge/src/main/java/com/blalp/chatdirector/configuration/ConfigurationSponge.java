package com.blalp.chatdirector.configuration;

import java.util.Map.Entry;

import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.sponge.SpongeModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationSponge extends Configuration {

    public ConfigurationSponge(String fileName) {
        super(fileName);
    }
    
    @Override
    public IModule loadModule(ObjectMapper om, Entry<String, JsonNode> module) {
        switch (module.getKey()) {
            case "sponge":
                return new SpongeModule();
            default:
                return super.loadModule(om,module);
        }
    }

}