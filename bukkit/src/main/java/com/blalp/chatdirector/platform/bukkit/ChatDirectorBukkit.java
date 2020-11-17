package com.blalp.chatdirector.platform.bukkit;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.ConfigurationBukkit;
import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.modules.bukkit.BukkitCommand;
import com.blalp.chatdirector.modules.bukkit.BukkitInputDaemon;
import com.blalp.chatdirector.modules.bungee.FromBungeeDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatDirectorBukkit extends JavaPlugin implements PluginMessageListener {
    public static ChatDirectorBukkit instance;
    private ChatDirector chatDirector;

    @Override
    public void onEnable() {
        instance = this;
        try {
            chatDirector = new ChatDirector(new ConfigurationBukkit(),
                    this.getDataFolder().getAbsolutePath() + File.separatorChar + "config.yml");
            BukkitInputDaemon.instance = new BukkitInputDaemon();
            getServer().getPluginManager().registerEvents(BukkitInputDaemon.instance, this);
            this.getDataFolder().mkdirs();
            chatDirector.load();
            if (BukkitInputDaemon.instance != null) {
                BukkitInputDaemon.instance.onServerStart();
            }
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
        if (BukkitInputDaemon.instance != null) {
            BukkitInputDaemon.instance.onServerStop();
        }
        chatDirector.unload();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (FromBungeeDaemon.instance != null) {
            FromBungeeDaemon.trigger(channel, player, message);
        }
    }

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
