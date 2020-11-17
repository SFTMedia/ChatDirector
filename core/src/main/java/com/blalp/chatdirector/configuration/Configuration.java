package com.blalp.chatdirector.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IModule;
import com.blalp.chatdirector.modules.common.CommonModule;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = ConfigurationDeserializer.class)
public class Configuration implements IConfiguration {

    static {
        Configurations.addConfiguration(new Configuration());
    }

    boolean debug;
    public List<IModule> modules = new ArrayList<>();
    public Map<String, Chain> chains = new HashMap<String, Chain>();

    // https://stackoverflow.com/questions/58102069/how-to-do-a-partial-deserialization-with-jackson#58102226
    @Override
    public void load() {
        // The actual loading is done in ChatDirector
        if (debug) {
            System.out.println("Modules");
            for (IModule module : modules) {
                System.out.println(module);
            }
            System.out.println("Chains");
            for (String pipeKey : chains.keySet()) {
                System.out.println("Chain " + pipeKey);
                for (IItem item : chains.get(pipeKey).items) {
                    System.out.println(item);
                }
            }
        }
    }

    public Class<?> getModuleClass(String moduleType) {
        switch (moduleType) {
            case "common":
                return CommonModule.class;
            default:
                ChatDirector.log(Level.WARNING, "Module of type " + moduleType + " not found.");
                return null;
        }
    }

    @Override
    public Class<?> getItemClass(String itemType, List<IModule> inputModules) {
        for (IModule module : inputModules) {
            if (module.getItemNames().contains(itemType)) {
                return module.getItemClass(itemType);
            }
        }
        return null;
    }

    @Override
    public void unload() {
    }

    @Override
    public Class<?> getItemClass(String itemType) {
        return getItemClass(itemType, modules);
    }

}