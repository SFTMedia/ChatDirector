package com.blalp.chatdirector.configuration;
import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.vault.VaultModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
public class ConfigurationBukkit extends Configuration {
    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "bukkit":
                return BukkitModule.class;
            case "vault":
                return VaultModule.class;
            case "bungee":
                return BungeeModule.class;
            default:
                return super.getModuleClass(moduleType);
        }
    }
    
}