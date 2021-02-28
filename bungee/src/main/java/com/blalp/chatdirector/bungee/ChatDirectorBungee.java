package com.blalp.chatdirector.bungee;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.bungee.modules.bungee.BungeeInputItemDaemon;
import com.blalp.chatdirector.bungee.modules.bungee.ReloadCommand;
import com.blalp.chatdirector.bungee.modules.multichat.MultiChatInputItemDaemon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ChatDirectorBungee extends Plugin {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    public ChatDirectorBungee() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // First thing's first, register the reload command
        ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance,
                new ReloadCommand("chatdirectorlocal"));
        try {
            chatDirector = new ChatDirector(new File(this.getDataFolder(), "config.yml"));
            getProxy().getPluginManager().registerListener(this,
                    (BungeeInputItemDaemon) ChatDirector.getConfig().getOrCreateDaemon(BungeeInputItemDaemon.class));
            getProxy().getPluginManager().registerListener(this, (MultiChatInputItemDaemon) ChatDirector.getConfig()
                    .getOrCreateDaemon(MultiChatInputItemDaemon.class));
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
