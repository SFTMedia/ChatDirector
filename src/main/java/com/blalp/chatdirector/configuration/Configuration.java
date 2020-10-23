package com.blalp.chatdirector.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.internalModules.common.NullItem;
import com.blalp.chatdirector.internalModules.format.Formatters;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.model.BaseConfiguration;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Pipe;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.modules.bukkit.BukkitModule;
import com.blalp.chatdirector.modules.bungee.BungeeModule;
import com.blalp.chatdirector.modules.conditional.ConditionalModule;
import com.blalp.chatdirector.modules.console.ConsoleModule;
import com.blalp.chatdirector.modules.discord.DiscordModule;
import com.blalp.chatdirector.modules.file.FileModule;
import com.blalp.chatdirector.modules.luckperms.LuckPerms;
import com.blalp.chatdirector.modules.replacement.ReplacementModule;
import com.blalp.chatdirector.modules.sponge.SpongeModule;
import com.blalp.chatdirector.modules.vault.Vault;

import org.apache.commons.lang.NullArgumentException;
import org.yaml.snakeyaml.Yaml;

public class Configuration extends Loadable {

    String fileName;
    public List<IModule> loadedModules = new ArrayList<>();
    public HashMap<String,Pipe> pipes = new HashMap<String,Pipe>();

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load() {
//        Constructor constructor = new Constructor(BaseConfiguration.class);
//        TypeDescription typeDescription = new TypeDescription(BaseConfiguration.class);
//        typeDescription.putMapPropertyType("modules", String.class, Module.class);
//        constructor.addTypeDescription(typeDescription);
        Yaml yaml = new Yaml();
        try {
            BaseConfiguration baseConfiguration = new BaseConfiguration();
            Map<String,Object> configuration = (Map<String,Object>)yaml.load(new FileReader(fileName));
            if(configuration.containsKey("debug")){
                baseConfiguration.debug=Boolean.parseBoolean((String)configuration.get("debug"));
            }
            if (configuration.containsKey("modules")) {
                for (Object key : (Iterable<Object>)configuration.get("modules")) {
                    loadedModules.add(loadModule(key));
                }
            }
            if (configuration.containsKey("pipes")) {
                for (LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>> outerKey : ((ArrayList<LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>>>)configuration.get("pipes"))) {
                    for (String key : outerKey.keySet()) {
                        pipes.put(key,loadPipe(key, outerKey.get(key)));
                    }
                }
            }
            
            System.out.println("Formatters");
            for (IFormatter formatter : ((Formatters)ChatDirector.formatter).formatters) {
                System.out.println(formatter);
            }
            System.out.println("Modules");
            for (IModule module : loadedModules) {
                System.out.println(module);
            }
            System.out.println("Pipes");
            for (String pipeKey: pipes.keySet()) {
                System.out.println("Pipe "+pipeKey);
                System.out.println("Root item "+pipes.get(pipeKey).rootItem);
            }


        } catch (FileNotFoundException e) {
            System.err.println("CONFIG NOT FOUND!");
            e.printStackTrace();
        }
    }
    private Pipe loadPipe(String name, ArrayList<LinkedHashMap<String,Object>> pipeObj) {
        Pipe pipe = new Pipe();
        IItem nullItem = new NullItem();
        IItem lastItem = null;
        IItem item=nullItem;
        LinkedHashMap<String,Object> pipeVal=null;
        for(Object pipeValObj:pipeObj) {
            try {
                pipeVal = (LinkedHashMap<String, Object>) pipeValObj;
            } catch (ClassCastException e){
                System.err.println("Make sure to have a `: null` after any items that don't require configuration.");
                continue;
            }
            item = loadItem(pipeVal.keySet().toArray()[0].toString(),pipeVal.values().toArray()[0]);
            if(pipe.rootItem==null){
                pipe.rootItem=item;
            }
            if(lastItem instanceof Item){
                ((Item)item).next=item;
            }
        }
        if(item instanceof Item){
            ((Item)item).next=nullItem;
        }
        return pipe;
    }
    private IItem loadItem(String key, Object item) {
        for (IModule iModule : loadedModules) {
            for(String itemType: iModule.getItemNames()){
                if(key.equalsIgnoreCase(itemType)){
                    return iModule.createItem(key, item);
                }
            }
        }
        try {
            throw new NullPointerException("Item of type "+key+" Not found.");
        } catch (NullPointerException e){
            e.printStackTrace();
            System.err.println("Item of type "+key+" Not found.");
        }
        return null;
    }
    private IModule loadModule(Object module) {
        String type="";
        if (module instanceof String){
            type=(String)module;
        } else if (module instanceof List){
            type=(String)((List)module).get(0);
        } else if (module instanceof Map){
            type=(String)((Map)module).keySet().toArray()[0];
        }
        switch (type) {
            case "bukkit":
                return new BukkitModule();
            case "bungee":
                return new BungeeModule();
            case "conditional":
                return new ConditionalModule();
            case "console":
                return new ConsoleModule();
            case "discord":
                return new DiscordModule((LinkedHashMap<String,ArrayList<LinkedHashMap<String,String>>>) ((Map)module).get(type));
            case "file":
                return new FileModule();
            case "luckperms":
                return new LuckPerms();
            case "replacement":
                return new ReplacementModule();
            case "sponge":
                return new SpongeModule();
            case "vault":
                return new Vault();
            default:
                throw new NullArgumentException("Module "+type+" not found.");
        }
    }

    @Override
    public void unload() {
        loadedModules = new ArrayList<>();
        pipes = new HashMap<String,Pipe>();
    }
    
}