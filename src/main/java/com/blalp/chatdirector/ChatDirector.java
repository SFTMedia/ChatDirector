package com.blalp.chatdirector;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.internalModules.format.Formatter;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.internalModules.permissions.DenyPermission;
import com.blalp.chatdirector.internalModules.permissions.IPermission;
import com.blalp.chatdirector.model.Loadable;

// Should implement both bungee, sponge and bukkit if possible
public class ChatDirector extends Loadable {
    public static void main(String[] args) {
        ChatDirector chatDirector = new ChatDirector();
        chatDirector.load();
    }
    public static IFormatter formatter = new Formatter();
    public static IPermission permission = new DenyPermission();
    public void load(){
        // Load config
        Configuration config = new Configuration("config.yml");
        config.load();
        // Load modules
        // read config
    }
    public void unload(){
        // call load again.
    }
}