package com.blalp.chatdirector.configuration;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.vault.VaultModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
public class ConfigurationBukkit extends Configuration {

    public ConfigurationBukkit(String fileName) {
        super(fileName);
    }

    @Override
    public IModule loadModule(ObjectMapper om, String moduleKey, JsonNode moduleValue) {
        switch (moduleKey) {
            case "bukkit":
                return new BukkitModule();
            case "vault":
                return new VaultModule();
            case "bungee":
                return new BungeeModule();
            default:
                return super.loadModule(om, moduleKey, moduleValue);
        }
    }
    
}