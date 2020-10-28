package com.blalp.chatdirector;

import java.io.File;

import com.blalp.chatdirector.configuration.ConfigurationBukkit;
import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.modules.bukkit.BukkitCommand;
import com.blalp.chatdirector.modules.bukkit.BukkitCommandItem;
import com.blalp.chatdirector.modules.bungee.FromBungeeDaemon;
import com.blalp.chatdirector.modules.common.ReloadItem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatDirectorBukkit extends JavaPlugin implements PluginMessageListener {
    public static ChatDirectorBukkit instance;
    private ChatDirector chatDirector;

    public ChatDirectorBukkit() {
        try {
            instance = this;
            chatDirector = new ChatDirector(new ConfigurationBukkit(this.getDataFolder().getAbsolutePath()+File.separatorChar+"config.yml"));
            this.getDataFolder().mkdirs();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("YIKES! Some error. Registering /chatdirector for you so you can reload.");
            registerReload();
        }
    }
    private void registerReload(){
        // In case anything goes wrong, register the reload command
        Item item = new BukkitCommandItem("chatdirector","chatdirector.reload");
        item.next=new ReloadItem();
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

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.trigger(channel, player, message);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for(BukkitCommand bukkitCommand : BukkitCommand.commands) {
            bukkitCommand.execute(sender, command, label, args);
        }
        return false;
    }
}
