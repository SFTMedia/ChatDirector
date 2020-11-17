package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.modules.javacord.DiscordModule;

public class DiscordConfiguration extends Configuration {
    static {
        Configurations.addConfiguration(new DiscordConfiguration());
    }

    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "discord":
                return DiscordModule.class;
            default:
                return super.getModuleClass(moduleType);
        }
    }
}
