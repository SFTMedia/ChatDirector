package com.blalp.chatdirector;

import java.io.File;

import com.blalp.chatdirector.configuration.ConfigurationBukkit;
import com.blalp.chatdirector.modules.bukkit.BukkitCommandInputDaemon;
import com.blalp.chatdirector.modules.bungee.FromBungeeDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatDirectorBukkit extends JavaPlugin implements PluginMessageListener {
    public static ChatDirectorBukkit instance;
    private ChatDirector chatDirector;

    public ChatDirectorBukkit() {
        instance = this;
        chatDirector = new ChatDirector(new ConfigurationBukkit(this.getDataFolder().getAbsolutePath()+File.separatorChar+"config.yml"));
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

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.trigger(channel, player, message);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(BukkitCommandInputDaemon.instance!=null){
            return BukkitCommandInputDaemon.instance.onCommand(sender,command,label,args);
        }
        if(command.equals("chatdirectorlocal")&&sender.hasPermission("chatdirector.reload")){
            sender.sendMessage("Starting Bukkit ChatDirector reload");
            ChatDirector.instance.reload();
            sender.sendMessage("Finished Bukkit ChatDirector reload");
        }
        return false;
    }
}
