package com.blalp.chatdirector.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.cache.CacheModule;
import com.blalp.chatdirector.modules.console.ConsoleModule;
import com.blalp.chatdirector.modules.context.ContextModule;
import com.blalp.chatdirector.modules.file.FileModule;
import com.blalp.chatdirector.modules.javacord.DiscordModule;
import com.blalp.chatdirector.modules.logic.LogicModule;
import com.blalp.chatdirector.modules.luckperms.LuckPermsModule;
import com.blalp.chatdirector.modules.replacement.ReplacementModule;
import com.blalp.chatdirector.modules.sql.SQLModule;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
@Data
@JsonDeserialize(using = ConfigurationDeserializer.class)
public class Configuration extends Loadable implements IConfiguration {

    boolean debug;
    public List<IModule> loadedModules = new ArrayList<>();
    public Map<String, Chain> chains = new HashMap<String, Chain>();

	// https://stackoverflow.com/questions/58102069/how-to-do-a-partial-deserialization-with-jackson#58102226
    @Override
    public void load() {
        // The actual loading is done in ChatDirector
        if(debug){
            System.out.println("Modules");
            for (IModule module : loadedModules) {
                System.out.println(module);
            }
            System.out.println("Chains");
            for (String pipeKey: chains.keySet()) {
                System.out.println("Chain "+pipeKey);
                for(IItem item : chains.get(pipeKey).items) {
                    System.out.println(item);
                }
            }
        }
    }

    public Class<?> getModuleClass(String moduleType) {
        switch(moduleType) {
            case "logic":
                return LogicModule.class;
            case "console":
                return ConsoleModule.class;
            case "context":
                return ContextModule.class;
            case "discord":
                return DiscordModule.class;
            case "file":
                return FileModule.class;
            case "luckperms":
                return LuckPermsModule.class;
            case "replacement":
                return ReplacementModule.class;
            case "cache":
                return CacheModule.class;
            case "sql":
                return SQLModule.class;
            default:
                ChatDirector.log(Level.WARNING, "Module of type "+moduleType+" not found.");
                return null;
        }
    }


    @Override
    public Class<?> getItemClass(String itemType) {
        for(IModule module: loadedModules){
            if(module.getItemNames().contains(itemType)){
                return module.getItemClass(itemType);
            }
        }
        return null;
    }

    @Override
    public void unload() {
    }
    
}