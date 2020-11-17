package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.modules.cache.CacheModule;
import com.blalp.chatdirector.modules.console.ConsoleModule;
import com.blalp.chatdirector.modules.context.ContextModule;
import com.blalp.chatdirector.modules.file.FileModule;
import com.blalp.chatdirector.modules.logic.LogicModule;
import com.blalp.chatdirector.modules.replacement.ReplacementModule;

public class ConfigurationCommon extends Configuration {
    static {
        Configurations.addConfiguration(new ConfigurationCommon());
    }

    @Override
    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "cache":
                return CacheModule.class;
            case "console":
                return ConsoleModule.class;
            case "context":
                return ContextModule.class;
            case "file":
                return FileModule.class;
            case "logic":
                return LogicModule.class;
            case "replacement":
                return ReplacementModule.class;
            default:
                return super.getModuleClass(moduleType);
        }
    }
}
