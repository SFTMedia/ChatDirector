package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.sponge.SpongeModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationSponge extends Configuration {

    public ConfigurationSponge(String fileName) {
        super(fileName);
    }
    
    @Override
    public IItem loadModule(ObjectMapper om, String moduleKey, JsonNode moduleValue) {
        switch (moduleKey) {
            case "sponge":
                return new SpongeModule();
            default:
                return super.loadModule(om,moduleKey, moduleValue);
        }
    }

}