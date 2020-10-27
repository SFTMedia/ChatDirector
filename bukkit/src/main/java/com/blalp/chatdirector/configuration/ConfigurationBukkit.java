package com.blalp.chatdirector.configuration;

import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.vault.VaultModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
public class ConfigurationBukkit extends Configuration {

    public ConfigurationBukkit(String fileName) {
        super(fileName);
    }

    @Override
    public IModule loadModule(Object module) {
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
            case "bukkit":
                return new BukkitModule();
            case "vault":
                return new VaultModule();
            case "bungee":
                return new BungeeModule();
            default:
                throw new NullPointerException("Module "+type+" not found.");
        }
    }
    
}