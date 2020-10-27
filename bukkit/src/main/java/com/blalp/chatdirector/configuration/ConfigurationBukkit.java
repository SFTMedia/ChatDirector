package com.blalp.chatdirector.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.internalModules.common.CommonModule;
import com.blalp.chatdirector.internalModules.common.NullItem;
import com.blalp.chatdirector.internalModules.format.Formatters;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Pipe;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.vault.VaultModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;

import org.yaml.snakeyaml.Yaml;

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