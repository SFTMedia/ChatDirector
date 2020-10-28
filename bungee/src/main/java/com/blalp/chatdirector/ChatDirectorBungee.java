package com.blalp.chatdirector;

import java.io.File;

import com.blalp.chatdirector.configuration.ConfigurationBungee;
import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.modules.bungee.BungeeCommand;
import com.blalp.chatdirector.modules.bungee.BungeeCommandItem;
import com.blalp.chatdirector.modules.bungee.BungeeInputItemDaemon;
import com.blalp.chatdirector.modules.common.ReloadItem;

import net.md_5.bungee.api.plugin.Plugin;


public class ChatDirectorBungee extends Plugin {
    public static ChatDirectorBungee instance;
    private ChatDirector chatDirector;

    public ChatDirectorBungee() {
        try {
            instance = this;
            chatDirector = new ChatDirector(new ConfigurationBungee(this.getDataFolder().getAbsolutePath()+File.separatorChar+"config.yml"));
            BungeeInputItemDaemon.instance=new BungeeInputItemDaemon();
            getProxy().getPluginManager().registerListener(this, BungeeInputItemDaemon.instance);
            this.getDataFolder().mkdirs();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("YIKES! Some error. Registering /chatdirector for you so you can reload.");
            registerReload();
        }
    }
    
    private void registerReload(){
        // In case anything goes wrong, register the reload command
        Item item = new BungeeCommandItem("chatdirector","chatdirector.reload");
        item.next=new ReloadItem();
        for(BungeeCommand command : BungeeCommand.commands){
            command.load();
        }
    }

    @Override
    public void onEnable() {
        try {
            chatDirector.load();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("YIKES! Some error. Registering /chatdirector for you so you can reload.");
            registerReload();
        }
    }

    @Override
    public void onDisable() {
        chatDirector.unload();
    }
}
