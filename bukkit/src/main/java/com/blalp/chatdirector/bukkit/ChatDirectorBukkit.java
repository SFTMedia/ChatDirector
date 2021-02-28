package com.blalp.chatdirector.bukkit;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.bukkit.modules.bukkit.BukkitCommand;
import com.blalp.chatdirector.bukkit.modules.bukkit.BukkitInputDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatDirectorBukkit extends JavaPlugin /*implements PluginMessageListener*/ {
    private ChatDirector chatDirector;

    @Override
    public void onEnable() {
        try {
            chatDirector = new ChatDirector(new File(this.getDataFolder(), "config.yml"));
            getServer().getPluginManager().registerEvents((BukkitInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class), this);
            this.getDataFolder().mkdirs();
            chatDirector.load();
            ((BukkitInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onServerStart();
            if (!ChatDirector.hasChains()) {
                throw new Exception("NO CHAINS!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("YIKES! Some error. Registering /chatdirectorlocal for you so you can reload.");
        }
    }

    @Override
    public void onDisable() {
        ((BukkitInputDaemon)ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onServerStop();
        chatDirector.unload();
    }
/*
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (FromBungeeDaemon.instance != null) {
            FromBungeeDaemon.trigger(channel, player, message);
        }
    }
*/
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("chatdirectorlocal") && sender.hasPermission("chatdirector.reload")) {
            new Thread(new TimedLoad()).start();
        }
        for (BukkitCommand bukkitCommand : BukkitCommand.commands) {
            bukkitCommand.execute(sender, command, label, args);
        }
        return false;
    }
}
