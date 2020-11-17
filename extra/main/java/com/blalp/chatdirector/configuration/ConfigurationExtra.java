package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.configuration.Configuration;

public class ConfigurationExtra extends Configuration {
    static {
        Configurations.addConfiguration(new ConfigurationExtra());
    }

    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "luckperms":
                return LuckPermsModule.class;
            case "sql":
                return SQLModule.class;
            default:
                return super.getModuleClass(moduleType);
                break;
        }

    }
}
