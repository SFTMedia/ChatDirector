package com.blalp.chatdirector.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IModule;

/**
 * This is a holder for multiple configurations, meant to be
 */
public class Configurations implements IConfiguration {

    /**
     * Maintain a list of all configurations
     */
    static List<IConfiguration> configurations = new ArrayList<>();
    List<IModule> modules = new ArrayList<>();
    Map<String, Chain> chains = new HashMap<String, Chain>();

    public static void addConfiguration(IConfiguration configuration) {
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
        return output;
    }

    @Override
    public Class<?> getItemClass(String itemType,List<IModule> inputModules) {
        Class<?> output = null;
        for (IConfiguration configuration : configurations) {
            output = configuration.getItemClass(itemType,inputModules);
            if (output != null) {
                break;
            }
        }
        return output;
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