package com.blalp.chatdirector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.internalModules.format.Formatters;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.model.Pipe;
import com.blalp.chatdirector.modules.IModule;

// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector extends Loadable {
    public static void main(String[] args) {
        ChatDirector chatDirector = new ChatDirector();
        chatDirector.load();
    }
    public static IFormatter formatter = new Formatters();
    List<IModule> modules = new ArrayList<>();
    HashMap<String,Pipe> pipes = new HashMap<String,Pipe>();
    Configuration config;
    public void load(){
        // Load config
        config = new Configuration("config.yml");
        config.load();
        modules=Configuration.loadedModules;
        pipes=config.pipes;
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