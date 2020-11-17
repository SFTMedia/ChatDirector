package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.modules.bungee.BungeeModule;
import com.blalp.chatdirector.modules.multichat.MultiChatModule;

public class ConfigurationBungee extends Configuration {
    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "bungee":
                return BungeeModule.class;
            case "multichat":
                return MultiChatModule.class;
            default:
            return super.getModuleClass(moduleType);
        }
    }
}