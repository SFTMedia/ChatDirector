package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.modules.sponge.SpongeModule;

public class ConfigurationSponge extends Configuration {
    static {
        Configurations.addConfiguration(new ConfigurationSponge());
    }
    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "sponge":
                return SpongeModule.class;
            default:
                return super.getModuleClass(moduleType);
        }
    }

}