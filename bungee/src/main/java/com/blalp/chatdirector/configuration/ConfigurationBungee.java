package com.blalp.chatdirector.configuration;

import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
import com.blalp.chatdirector.modules.multichat.MultiChatModule;

public class ConfigurationBungee extends Configuration {

    public ConfigurationBungee(String fileName) {
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
            case "bungee":
                return new BungeeModule();
            case "multichat":
                return new MultiChatModule();
            default:
                return null;
        }
    }
    
}