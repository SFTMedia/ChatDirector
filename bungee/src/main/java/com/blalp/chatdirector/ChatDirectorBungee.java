package com.blalp.chatdirector;

import java.io.File;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import com.blalp.chatdirector.ChatDirector;


public class ChatDirectorBungee extends Plugin {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    public ChatDirectorBungee() {
        instance = this;
        chatDirector = new ChatDirector();
        this.getDataFolder().mkdirs();
        chatDirector.path=this.getDataFolder().getAbsolutePath()+File.separatorChar+"config.yml";
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
