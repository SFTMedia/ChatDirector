package com.blalp.chatdirector.configuration;

import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.sponge.SpongeModule;

public class ConfigurationSponge extends Configuration {
    public ConfigurationSponge(String fileName) {
        super(fileName);
    }

    protected IModule loadModule(Object module) {
        IModule output = super.loadModule(module);
        if(output!=null){
            return output;
        }
        String type="";
        if (module instanceof String){
            type=(String)module;
        } else if (module instanceof List){
            type=(String)((List)module).get(0);
        } else if (module instanceof Map){
            type=(String)((Map)module).keySet().toArray()[0];
        }
        switch (type) {
            case "sponge":
                return new SpongeModule();
            default:
                throw null;
        }
    }
    
}