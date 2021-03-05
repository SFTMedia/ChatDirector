package com.blalp.chatdirector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Data;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

@Data
// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector implements IConfiguration {
    Configuration config = null;
    Configuration configStaging = null;
    static Logger logger;
    static Handler handler;
    /**
     * Maintain this list as some things run in separate threads, so with an item
     * you need to be able to get the chain object to start execution. Look for a
     * better solution.
     */
    static Map<IItem, Chain> items = new HashMap<>();
    static ChatDirector instance;
    // One of these three is populated with data
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
        config = new Configuration();
        instance = this;
        logger = Logger.getLogger("ChatDirector");
        this.file = new File("config.yml");
    }

    public boolean loadConfig(){
        boolean result = true;
        ObjectMapper om = new ObjectMapper(new YAMLFactory())
                .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        try {
            if (rawData != null) {
                configStaging = om.readValue(rawData, Configuration.class);
            } else if (stream != null) {
                configStaging = om.readValue(stream, Configuration.class);
            } else if (file != null) {
                configStaging = om.readValue(file, Configuration.class);
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.log(Level.SEVERE, "config failed to load.");
            return false;
        }
        return result;
    }

    public boolean load() {
        // Load config
        // NOTE: While config is being reloaded it will use the old config in parsing if
        // the singleton is used.
        if(!loadConfig()){
            return false;
        }
        // At this point config loaded
        if (config.getChains().size()!=0) {
            // ignore if unload fails, as we always want to load.
            unload();
        }
        config = configStaging;

        return config.load();
    }

    public boolean unload() {
        boolean result = true;
        for (IModule module : config.getModules()) {
            result = result && module.unload();
        }
        for (IDaemon daemon : config.getDaemons()) {
            daemon.unload();
        }
        result = result && config.unload();
        return result;
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

    public static Context run(IItem item, Context context, boolean async) {
        if (items.containsKey(item)) {
            if (async) {
                items.get(item).runAsync(item, context);
            } else {
                return items.get(item).runAt(item, context);
            }
        } else {
            logger.log(Level.SEVERE, "Could not find chain to go with " + item);
            return new Context().halt();
        }
        return new Context();
    }

    public static void addItem(IItem item, Chain chain) {
        items.put(item, chain);
    }

    public static boolean hasChains() {
        return getConfig().getChains().size() != 0;
    }

    public Class<? extends IItem> getItemClass(String itemType) {
        return config.getItemClass(itemType);
    }

    @Override
    public Map<String, Chain> getChains() {
        return config.getChains();
    }

    @Override
    public Class<? extends IItem> getItemClass(String itemType, Iterable<IModule> modules) {
        return config.getItemClass(itemType, modules);
    }

    public static Configuration getConfig() {
        if (instance.config != null) {
            return instance.config;
        } else {
            return instance.configStaging;
        }
    }

    public static Configuration getConfigStaging() {
        if (instance.configStaging != null) {
            return instance.configStaging;
        } else {
            return instance.config;
        }
    }

    public static ChatDirector getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public IModule getModule(Class<? extends IModule> class1) {
        return config.getModule(class1);
    }

    @Override
    public IDaemon getOrCreateDaemon(Class<? extends IDaemon> class1) {
        return config.getOrCreateDaemon(class1);
    }

    public boolean isDebug() {
        return getConfig().isDebug();
    }

    @Override
    public boolean isTesting() {
        return getConfig().isTesting();
    }

    @Override
    public boolean isValid() {
        return getConfig().isValid();
    }
}