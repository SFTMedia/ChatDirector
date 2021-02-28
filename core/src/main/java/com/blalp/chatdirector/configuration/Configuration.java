package com.blalp.chatdirector.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IModule;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = ConfigurationDeserializer.class)
public class Configuration implements IConfiguration {
    boolean debug;
    ServiceLoader<IModule> modules;
    Map<String, Chain> chains = new HashMap<String, Chain>();
    // This is for storage of generic keys that modules may need.
    // The first key is the module name
    Map<String, Map<String, String>> moduleData = new HashMap<>();

    public Configuration() {
        modules = ServiceLoader.load(IModule.class);
    }

    // https://stackoverflow.com/questions/58102069/how-to-do-a-partial-deserialization-with-jackson#58102226
    @Override
    public boolean load() {
        // The actual loading is done in ChatDirector
        if (debug) {
            System.out.println("Modules");
            for (IModule module : modules) {
                System.out.println(module);
            }
            System.out.println("Chains");
            for (String pipeKey : chains.keySet()) {
                System.out.println("Chain " + pipeKey);
                for (IItem item : chains.get(pipeKey).getItems()) {
                    System.out.println(item);
                }
            }
        }
        return true;
    }

    @Override
    public Class<?> getItemClass(String itemType, Iterable<IModule> inputModules) {
        for (IModule module : inputModules) {
            if (module.getItemNames().contains(itemType)) {
                return module.getItemClass(itemType);
            }
        }
        return null;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public Class<?> getItemClass(String itemType) {
        return getItemClass(itemType, modules);
    }

}