package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.bungeeMessage.BungeeMessageModule;
import com.blalp.chatdirector.modules.vault.VaultModule;

public class ConfigurationBukkit extends Configuration {
    static {
        Configurations.addConfiguration(new ConfigurationBukkit());
    }

    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "bukkit":
                return BukkitModule.class;
            case "vault":
                return VaultModule.class;
            case "bungee":
                return BungeeMessageModule.class;
            default:
                return super.getModuleClass(moduleType);
        }
    }

}