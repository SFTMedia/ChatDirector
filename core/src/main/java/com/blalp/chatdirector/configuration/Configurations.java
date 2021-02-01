package com.blalp.chatdirector.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IModule;

/**
 * This is a holder for multiple configurations, meant to be
 */
public class Configurations implements IConfiguration {

    /**
     * Maintain a list of all configurations
     */
    List<IConfiguration> configurations = new ArrayList<>();
    List<IModule> modules = new ArrayList<>();
    Map<String, Chain> chains = new HashMap<String, Chain>();

    public void addConfiguration(IConfiguration configuration) {
        configurations.add(configuration);
    }

    @Override
    public boolean load() {
        boolean result = true;
        for (IConfiguration configuration : configurations) {
            result=result&&configuration.load();
        }
        return result;
    }

    @Override
    public boolean unload() {
        boolean result = true;
        for (IConfiguration configuration : configurations) {
            result=result&&configuration.unload();
        }
        return result;
    }

    @Override
    public Class<?> getModuleClass(String moduleType) {
        Class<?> output = null;
        for (IConfiguration configuration : configurations) {
            output = configuration.getModuleClass(moduleType);
            if (output != null) {
                break;
            }
        }
        if (output==null) {
            ChatDirector.logger.log(Level.WARNING, "Module of type " + moduleType + " not found.");
        }
        return output;
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
    public List<IModule> getModules() {
        return modules;
    }

    @Override
    public Map<String, Chain> getChains() {
        return chains;
    }

    @Override
    public Class<?> getItemClass(String itemType) {
        return getItemClass(itemType,getModules());
    }

}