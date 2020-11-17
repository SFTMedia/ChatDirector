package com.blalp.chatdirector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.modules.IModule;
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
    public static Configuration config;
    static Logger logger;
    static Handler handler;
    /**
     * Maintain this list as some things run in separate threads, so with an item you need to be able to get the chain object to start execution. Look for a better solution.
     */
    static Map<IItem, Chain> items = new HashMap<>();
    public static ChatDirector instance;
    List<IModule> modules = new ArrayList<>();
    Map<String, Chain> chains = new HashMap<String, Chain>();
    String fileName;

    public ChatDirector(Configuration configuration,String fileName) {
        config = configuration;
        instance = this;
        logger = Logger.getLogger("ChatDirector");
        //handler = new ConsoleHandler();
        //handler.setLevel(Level.WARNING);
        //logger.addHandler(handler);
        this.fileName=fileName;
    }

    public void load() {
        // Load config
        ObjectMapper om = new ObjectMapper(new YAMLFactory()).setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        try {
            config=(om.readValue(new File(fileName),Configuration.class));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        } catch (IOException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        }
        modules = config.loadedModules;
        chains = config.chains;
        // Load modules
        for (IModule module : modules) {
            module.load();
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
        logger.log(Level.FINE, obj.toString());
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
        return config.chains.size() != 0;
	}

	public Class<?> getModuleClass(String moduleType) {
        return config.getModuleClass(moduleType);
	}

	public Class<?> getItemClass(String itemType) {
		return config.getItemClass(itemType);
	}
}