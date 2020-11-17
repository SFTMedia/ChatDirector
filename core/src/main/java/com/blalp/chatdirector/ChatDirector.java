package com.blalp.chatdirector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.configuration.Configurations;
import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.IModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IItem;

// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector extends Loadable implements IConfiguration {
    public static IConfiguration config;
    static Logger logger;
    static Handler handler;
    /**
     * Maintain this list as some things run in separate threads, so with an item
     * you need to be able to get the chain object to start execution. Look for a
     * better solution.
     */
    static Map<IItem, Chain> items = new HashMap<>();
    public static ChatDirector instance;
    List<IModule> modules = new ArrayList<>();
    Map<String, Chain> chains = new HashMap<String, Chain>();
    File file;
    InputStream stream;
    String rawData;

    public ChatDirector(InputStream stream) {
        this();
        this.stream = stream;
    }
    public ChatDirector(File file) {
        this();
        this.file = file;
    }
    public ChatDirector(String rawData) {
        this();
        this.rawData = rawData;
    }
    public ChatDirector() {
        config = new Configurations();
        instance = this;
        logger = Logger.getLogger("ChatDirector");
        this.file = new File("config.yml");
    }

    public void load() {
        // Load config
        ObjectMapper om = new ObjectMapper(new YAMLFactory())
                .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        try {
            if(rawData!=null){
                config = (om.readValue(rawData, Configuration.class));
            } else if (file!=null){
                config = (om.readValue(file, Configuration.class));
            } else if (stream!=null){
                config = (om.readValue(stream, Configuration.class));
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        } catch (IOException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        }
        modules = config.getModules();
        chains = config.getChains();
        // Load modules
        for (IModule module : modules) {
            module.load();
        }
        // Now validate chains
        for (Entry<String, Chain> chain : chains.entrySet()) {
            if (chain.getValue()!=null&&!chain.getValue().isValid()) {
                log(Level.SEVERE, "chain " + chain + " is not valid.");
            }
        }
        for (IModule module : modules) {
            if (!module.isValid()) {
                log(Level.SEVERE, "module " + module + " is not valid.");
            }
        }
    }

    public void unload() {
        for (IModule module : modules) {
            module.unload();
        }
        config.unload();
        modules = new ArrayList<>();
        chains = new HashMap<String, Chain>();
    }

    public static String format(Context context) {
        return format(context.getCurrent(), context);
    }

    public static String format(String format, Context context) {
        synchronized (context) {
            for (Entry<String, String> singleContext : context.entrySet()) {
                if (singleContext.getKey() != null & singleContext.getValue() != null) {
                    format = format.replace("%" + singleContext.getKey() + "%", singleContext.getValue());
                }
            }
        }
        return format;
    }

    public static void logDebug(Object obj) {
        if(obj!=null){
            logger.log(Level.FINE, obj.toString());
        } else {
            logger.log(Level.FINE, "REQUESTED TO LOG NULL!");
        }
    }

    public static void log(Level level, String string) {
        logger.log(level, string);
    }

    public static Context run(IItem item, Context context, boolean async) {
        if (items.containsKey(item)) {
            if (async) {
                items.get(item).runAsync(item, context);
            } else {
                return items.get(item).runAt(item, context);
            }
        } else {
            log(Level.SEVERE, "Could not find chain to go with " + item);
            return new Context().halt();
        }
        return new Context();
    }

    public static void addItem(IItem item, Chain chain) {
        items.put(item, chain);
    }

    public static boolean hasChains() {
        return config.getChains().size() != 0;
    }

    public Class<?> getModuleClass(String moduleType) {
        return config.getModuleClass(moduleType);
    }

    public Class<?> getItemClass(String itemType) {
        return config.getItemClass(itemType);
    }

    @Override
    public List<IModule> getModules() {
        return config.getModules();
    }

    @Override
    public Map<String, Chain> getChains() {
        return config.getChains();
    }

    @Override
    public Class<?> getItemClass(String itemType, List<IModule> modules) {
        return config.getItemClass(itemType, modules);
    }
}