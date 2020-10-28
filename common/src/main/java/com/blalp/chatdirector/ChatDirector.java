package com.blalp.chatdirector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.format.Formatters;
import com.blalp.chatdirector.model.format.IFormatter;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Pipe;
import com.blalp.chatdirector.modules.IModule;

// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector extends Loadable {
    public static Configuration config;
    public static void main(String[] args) {
        ChatDirector chatDirector = new ChatDirector(new Configuration("config.yml"));
        chatDirector.load();
    }
    public ChatDirector(Configuration configuration){
        config=configuration;
        instance=this;
    }
    public static IFormatter formatter = new Formatters();
	public static ChatDirector instance;
    List<IModule> modules = new ArrayList<>();
    HashMap<String,Pipe> pipes = new HashMap<String,Pipe>();
    public void load(){
        // Load config
        config.load();
        modules=Configuration.loadedModules;
        pipes=config.chains;
        // Load modules
        for(IModule module:modules){
            module.load();
        }
    }
    public void unload(){
        for(IModule module:modules){
            module.unload();
        }
        config.unload();
        modules= new ArrayList<>();
        pipes= new HashMap<String,Pipe>();
    }
	public static void addFormatter(IFormatter newFormatter) {
        ((Formatters)formatter).formatters.add(newFormatter);
	}
}