package com.blalp.chatdirector;

import java.io.File;

import com.blalp.chatdirector.configuration.ConfigurationBungee;
import com.blalp.chatdirector.modules.bungee.BungeeInputItemDaemon;

import net.md_5.bungee.api.plugin.Plugin;


public class ChatDirectorBungee extends Plugin {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    public ChatDirectorBungee() {
        instance = this;
        chatDirector = new ChatDirector(new ConfigurationBungee(this.getDataFolder().getAbsolutePath()+File.separatorChar+"config.yml"));
        BungeeInputItemDaemon.instance=new BungeeInputItemDaemon();
		getProxy().getPluginManager().registerListener(this, BungeeInputItemDaemon.instance);
        this.getDataFolder().mkdirs();
    }
    
    @Override
    public void onEnable() {
        chatDirector.load();
    }

    @Override
    public void onDisable() {
        chatDirector.unload();
    }
}
