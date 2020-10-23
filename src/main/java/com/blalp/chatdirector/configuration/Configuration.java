package com.blalp.chatdirector.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.model.BaseConfiguration;
import com.blalp.chatdirector.model.Loadable;
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

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Configuration extends Loadable {

    String fileName;
    List<IModule> loadedModules = new ArrayList<>();

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
                    String type="";
                    if (key instanceof String){
                        type=(String)key;
                    } else if (key instanceof List){
                        type=(String)((List)key).get(0);
                    } else if (key instanceof Map){
                        type=(String)((Map)key).keySet().toArray()[0];
                    }
                    switch (type) {
                        case "bukkit":
                            loadedModules.add(new BukkitModule());
                            break;
                        case "bungee":
                            loadedModules.add(new BungeeModule());
                            break;
                        case "conditional":
                            loadedModules.add(new ConditionalModule());
                            break;
                        case "console":
                            loadedModules.add(new ConsoleModule());
                            break;
                        case "discord":
                            loadedModules.add(new DiscordModule((LinkedHashMap<String,ArrayList<LinkedHashMap<String,String>>>) ((Map)key).get(type)));
                            break;
                        case "file":
                            loadedModules.add(new FileModule());
                            break;
                        case "luckperms":
                            loadedModules.add(new LuckPerms());
                            break;
                        case "replacement":
                            loadedModules.add(new ReplacementModule());
                            break;
                        case "sponge":
                            loadedModules.add(new SpongeModule());
                            break;
                        case "vault":
                            loadedModules.add(new Vault());
                            break;
                    }
                }
            }
            if (configuration.containsKey("pipes")) {

            }
            System.out.println(configuration);
        } catch (FileNotFoundException e) {
            System.err.println("CONFIG NOT FOUND!");
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {

    }
    
}