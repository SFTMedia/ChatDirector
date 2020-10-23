package com.blalp.chatdirector;

import com.blalp.chatdirector.modules.bungee.FromBungeeDaemon;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatDirectorBukkit extends JavaPlugin implements PluginMessageListener {
    public static ChatDirectorBukkit instance;

    public ChatDirectorBukkit() {
        instance = this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.trigger(channel, player, message);
        }
    }
}
