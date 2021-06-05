package com.blalp.chatdirector.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.blalp.chatdirector.core.configuration.Configuration;
import com.blalp.chatdirector.core.model.IModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Data;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IConfiguration;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.ILoadable;

@Data
// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector implements IConfiguration {
    Configuration config = null;
    Configuration configStaging = null;
    static Logger logger;
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

    public boolean loadConfig() {
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
        return true;
    }

    public boolean load() {
        // Load config
        // NOTE: While config is being reloaded it will use the old config in parsing if
        // the singleton is used.
        if (!loadConfig()) {
            logger.warning("New config failed to load, keeping old config...");
            return false;
        }
        logger.info("New config loaded, unloading old config...");
        // At this point configStaging loaded
        // only unload the existing config if it was valid (aka has chains)
        if (config.getChains().size() != 0) {
            // ignore if unload fails, as we always want to load.
            config.unload();
        }
        config = configStaging;

        return config.load();
    }

    public boolean unload() {
        boolean result = true;
        for (IModule module : config.getModules()) {
            result = result && module.unload();
        }
        for (ILoadable daemon : config.getDaemons().values()) {
            daemon.unload();
        }
        result = result && config.unload();
        return result;
    }

    public static String format(Context context) {
        return format(context.getCurrent(), context);
    }

    public static String format(String format, Context context) {
        if(format==null){
            return "";
        }
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
        Chain chain = ChatDirector.getConfig().getChainForItem(item);
        if (chain != null) {
            if (async) {
                chain.runAsync(item, context);
                return new Context();
            } else {
                return chain.runAt(item, context);
            }
        } else {
            logger.log(Level.SEVERE, "Could not find chain to go with " + item);
            return new Context().halt();
        }
    }

    public static void addItem(IItem item, Chain chain) {
        ChatDirector.getConfig().putChainForItem(item, chain);
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
        return instance.config;
    }

    public static Configuration getConfigStaging() {
        return instance.configStaging;
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
    public ILoadable getOrCreateDaemon(Class<? extends ILoadable> class1) {
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