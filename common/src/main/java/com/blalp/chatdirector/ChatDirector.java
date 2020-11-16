package com.blalp.chatdirector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector extends Loadable {
    static Configuration config;
    static Logger logger;
    static Handler handler;
    static Map<IItem, Chain> listenerItems = new HashMap<>();
    public static ChatDirector instance;
    List<IModule> modules = new ArrayList<>();
    HashMap<String, Chain> chains = new HashMap<String, Chain>();

    public ChatDirector(Configuration configuration) {
        config = configuration;
        instance = this;
        logger = Logger.getLogger("ChatDirector");
        handler = new ConsoleHandler();
        handler.setLevel(Level.WARNING);
        logger.addHandler(handler);
    }

    public void load() {
        // Load config
        config.load();
        modules = Configuration.loadedModules;
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
        if (listenerItems.containsKey(item)) {
            if (async) {
                listenerItems.get(item).runAsync(item, context);
            } else {
                return listenerItems.get(item).runAt(item, context);
            }
        } else {
            log(Level.SEVERE, "Could not find chain to go with " + item);
            return new Context().halt();
        }
        return new Context();
    }

    public static Chain loadChain(ObjectMapper om, JsonNode module) {
        return config.loadChain(om, module);
    }

    public static IModule loadModule(ObjectMapper om, Entry<String, JsonNode> module) {
        return config.loadModule(om, module);
    }

    public static IItem loadItem(ObjectMapper om, Chain chain, String key, JsonNode item) {
        return config.loadItem(om, chain, key, item);
    }

	public static void addListenerItem(IItem item, Chain chain) {
        listenerItems.put(item, chain);
	}

	public static boolean hasChains() {
		return config.chains.size()!=0;
	}
}