package com.blalp.chatdirector.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IteratorIterable;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.IConfiguration;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.cache.CacheModule;
import com.blalp.chatdirector.modules.console.ConsoleModule;
import com.blalp.chatdirector.modules.context.ContextModule;
import com.blalp.chatdirector.modules.file.FileModule;
import com.blalp.chatdirector.modules.javacord.DiscordModule;
import com.blalp.chatdirector.modules.logic.LogicModule;
import com.blalp.chatdirector.modules.luckperms.LuckPermsModule;
import com.blalp.chatdirector.modules.replacement.ReplacementModule;
import com.blalp.chatdirector.modules.sql.SQLModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Configuration extends Loadable implements IConfiguration {

    String fileName;
    public static List<IModule> loadedModules = new ArrayList<>();
    public static boolean debug;
    public HashMap<String, Chain> chains = new HashMap<String, Chain>();

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load() {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            JsonNode root = om.readTree(new File(fileName));
            debug=root.at("debug").asBoolean();
            for (Entry<String, JsonNode> module : new IteratorIterable<Entry<String, JsonNode>>(root.at("modules").fields())) {
                loadModule(om, module);
            }
            for (Entry<String, JsonNode> module : new IteratorIterable<Entry<String, JsonNode>>(root.at("chains").fields())) {
                chains.put(module.getKey(),loadChain(om, module.getValue()));
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        } catch (IOException e1) {
            e1.printStackTrace();
            new Thread(new TimedLoad()).start();
        }
        if(debug){
            System.out.println("Modules");
            for (IModule module : loadedModules) {
                System.out.println(module);
            }
            System.out.println("Chains");
            for (String pipeKey: chains.keySet()) {
                System.out.println("Chain "+pipeKey);
                for(IItem item : chains.get(pipeKey).items) {
                    System.out.println(item);
                }
            }
        }
    }
    
    public Chain loadChain(ObjectMapper om, JsonNode module) {
        Chain chain = new Chain();
        for (Entry<String, JsonNode> item : new IteratorIterable<>(module.fields())) {
            chain.items.add(loadItem(om, chain, item.getKey(), item.getValue()));
        }
        return chain;        
    }

    public IModule loadModule(ObjectMapper om, Entry<String, JsonNode> module) {
        switch(module.getKey()) {
            case "logic":
                return om.convertValue(module.getValue(), LogicModule.class);
            case "console":
                return om.convertValue(module.getValue(), ConsoleModule.class);
            case "context":
                return om.convertValue(module.getValue(), ContextModule.class);
            case "discord":
                return om.convertValue(module.getValue(), DiscordModule.class);
            case "file":
                return om.convertValue(module.getValue(), FileModule.class);
            case "luckperms":
                return om.convertValue(module.getValue(), LuckPermsModule.class);
            case "replacement":
                return om.convertValue(module.getValue(), ReplacementModule.class);
            case "cache":
                return om.convertValue(module.getValue(), CacheModule.class);
            case "sql":
                return om.convertValue(module.getValue(), SQLModule.class);
            default:
                ChatDirector.log(Level.WARNING, "Module of type "+module.getKey()+" not found.");
                return null;
        }
    }

    public IItem loadItem(ObjectMapper om, Chain chain, String key, JsonNode item) {
        for(IModule module : loadedModules) {
            if (module.getItemNames().contains(key)) {
                return module.createItem(om, chain, key, item);
            }
        }
        System.err.println("Item of type "+key+" Not found.");
        return null;
    }

    @Override
    public void unload() {
        loadedModules = new ArrayList<>();
        chains = new HashMap<String,Chain>();
    }
    
}