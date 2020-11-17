package com.blalp.chatdirector.platform.bungee;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;

import com.blalp.chatdirector.modules.bungee.BungeeInputItemDaemon;
import com.blalp.chatdirector.modules.multichat.MultiChatInputItemDaemon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ChatDirectorBungee extends Plugin {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    @Override
    public void onEnable() {
        instance = this;
        // First thing's first, register the reload command
        ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance,
                new ReloadCommand("chatdirectorlocal"));
        try {
            chatDirector = new ChatDirector(new File(this.getDataFolder(),"config.yml"));
            BungeeInputItemDaemon.instance = new BungeeInputItemDaemon();
            getProxy().getPluginManager().registerListener(this, BungeeInputItemDaemon.instance);
            MultiChatInputItemDaemon.instance = new MultiChatInputItemDaemon();
            getProxy().getPluginManager().registerListener(this, MultiChatInputItemDaemon.instance);
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
}
