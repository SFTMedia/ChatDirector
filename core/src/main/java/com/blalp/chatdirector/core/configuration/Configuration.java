package com.blalp.chatdirector.core.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.ILoadable;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.IConfiguration;
import com.blalp.chatdirector.core.model.IModule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = ConfigurationDeserializer.class)
public class Configuration implements IConfiguration {
    boolean debug;
    String version;
    // Do not allow the user to specify whether or not they are in testing mode,
    // that should only be done programmatically in the unit tests.
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    boolean testing = false;
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    ServiceLoader<IModule> modules;
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    Map<Class<?>, ILoadable> daemons = new ConcurrentHashMap<>();
    Map<String, Chain> chains = new HashMap<String, Chain>();
    // This is for storage of generic keys that modules may need.
    // The first key is the module name
    Map<String, Map<String, String>> moduleData = new HashMap<>();
    /**
     * Maintain this list as some things run in separate threads, so with an item
     * you need to be able to get the chain object to start execution. Look for a
     * better solution.
     */
    @JsonIgnore
    static Map<IItem, Chain> items = new HashMap<>();

    public Configuration() {
        modules = ServiceLoader.load(IModule.class, this.getClass().getClassLoader());
        try {
            Class.forName("org.junit.jupiter.api.Test");
            testing = true;
        } catch (ClassNotFoundException e) {
            testing = false;
        }
    }

    // https://stackoverflow.com/questions/58102069/how-to-do-a-partial-deserialization-with-jackson#58102226
    @Override
    public boolean load() {
        if (debug) {
            System.out.println("Modules");
            for (IModule module : modules) {
                System.out.println("\t" + module);
            }
            System.out.println();
            System.out.println("Module Data");
            for (Entry<String, Map<String, String>> module : moduleData.entrySet()) {
                System.out.println("\t" + module.getKey() + ": ");
                for (Entry<String, String> item : module.getValue().entrySet()) {
                    System.out.println("\t\t" + item.getKey() + ": " + item.getValue());
                }
            }
            System.out.println();
            System.out.println("Daemons");
            for (ILoadable daemon : daemons.values()) {
                System.out.println("\t" + daemon);
            }
            System.out.println();
            System.out.println("Chains");
            for (String pipeKey : chains.keySet()) {
                System.out.println("\t" + "Chain " + pipeKey);
                if (chains.get(pipeKey) != null) {
                    for (IItem item : chains.get(pipeKey).getItems()) {
                        System.out.println("\t\t" + item);
                    }
                }
            }
        }
        if (debug) {
            System.out.println();
            System.out.println("Loading Modules...");
        }
        for (IModule module : getModules()) {
            if (debug) {
                System.out.println("\t" + module);
            }
            if (!module.load()) {
                ChatDirector.getLogger().log(Level.SEVERE,
                        "Module " + module.getClass() + " " + module + " failed to load.");
                return false;
            }
        }
        if (debug) {
            System.out.println();
            System.out.println("Checking Validity...");
        }
        if (!isValid()) {
            return false;
        }
        if (debug) {
            System.out.println();
            System.out.println("Loading Daemons...");
        }
        for (ILoadable daemon : getDaemons().values()) {
            System.out.println("\t" + daemon);
            if (!daemon.load()) {
                ChatDirector.getLogger().log(Level.SEVERE,
                        "daemon " + daemon.getClass() + " " + daemon + " failed to load.");
                return false;
            }
        }
        return true;
    }

    @Override
    public Class<? extends IItem> getItemClass(String itemType, Iterable<IModule> inputModules) {
        for (IModule module : inputModules) {
            if (module.getItemNames().contains(itemType)) {
                return module.getItemClass(itemType);
            }
        }
        return null;
    }

    @Override
    public boolean unload() {
        for (ILoadable daemon : daemons.values()) {
            daemon.unload();
        }
        for (IModule module : getModules()) {
            module.unload();
        }
        return true;
    }

    @Override
    public Class<? extends IItem> getItemClass(String itemType) {
        return getItemClass(itemType, modules);
    }

    public IModule getModule(Class<? extends IModule> class1) {
        for (IModule module : modules) {
            if (module.getClass().equals(class1)) {
                return module;
            }
        }
        return null;
    }

    public boolean hasDaemon(Class<? extends ILoadable> class1) {
        return daemons.containsKey(class1);
    }

    public ILoadable getOrCreateDaemon(Class<? extends ILoadable> class1) {
        if (daemons.containsKey(class1)) {
            return daemons.get(class1);
        } else {
            try {
                ILoadable daemon = (ILoadable) class1.getConstructors()[0].newInstance();
                daemons.put(class1, daemon);
                return daemon;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isValid() {
        for (Entry<String, Chain> chain : getChains().entrySet()) {
            if (chain.getValue() != null && !chain.getValue().isValid()) {
                ChatDirector.getLogger().log(Level.SEVERE, "chain: " + chain.toString() + " is not valid.");
                return false;
            }
        }
        for (IModule module : getModules()) {
            if (!module.isValid()) {
                ChatDirector.getLogger().log(Level.SEVERE, "module " + module.toString() + " is not valid.");
                return false;
            }
        }
        // Make sure there are chains
        return getChains().size() != 0;
    }

    public Chain getChainForItem(IItem item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return null;
    }

    public void putChainForItem(IItem item, Chain chain) {
        items.put(item, chain);
    }
}