package com.blalp.chatdirector.bungee;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.bungee.modules.bungee.BungeeInputDaemon;
import com.blalp.chatdirector.bungee.modules.bungee.ReloadCommand;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ChatDirectorBungee extends Plugin implements Listener {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    public ChatDirectorBungee() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // First thing's first, register the reload command
        ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance,
                new ReloadCommand("chatdirector"));
        try {
            chatDirector = new ChatDirector(new File(this.getDataFolder(), "config.yml"));
            this.getDataFolder().mkdirs();
            chatDirector.load();
            if (!ChatDirector.hasChains()) {
                throw new Exception("NO CHAINS!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        chatDirector.unload();
    }


    @EventHandler
    public void onEvent(PlayerDisconnectEvent e) {
        ((BungeeInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BungeeInputDaemon.class)).onEvent(e);
    }

    @EventHandler
    public void onEvent(ServerSwitchEvent e) {
        ((BungeeInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BungeeInputDaemon.class)).onEvent(e);
    }

    @EventHandler
    public void onEvent(ServerConnectedEvent e) {
        ((BungeeInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BungeeInputDaemon.class)).onEvent(e);
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        ((BungeeInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BungeeInputDaemon.class)).onEvent(e);
    }
}
