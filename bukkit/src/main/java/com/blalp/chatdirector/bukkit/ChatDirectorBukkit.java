package com.blalp.chatdirector.bukkit;

import java.io.File;

import com.blalp.chatdirector.core.configuration.TimedLoad;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.bukkit.modules.bukkit.BukkitCommandDaemon;
import com.blalp.chatdirector.bukkit.modules.bukkit.BukkitInputDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatDirectorBukkit extends JavaPlugin implements Listener {
    private ChatDirector chatDirector;
    public static ChatDirectorBukkit instance;

    @Override
    public void onEnable() {
        instance = this;
        try {
            chatDirector = new ChatDirector(new File(this.getDataFolder(), "config.yml"));
            this.getDataFolder().mkdirs();
            getServer().getPluginManager().registerEvents(this, this);
            chatDirector.load();
            ((BukkitInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onServerStart();
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
        ((BukkitInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onServerStop();
        chatDirector.unload();
    }

    /*
     * @Override public void onPluginMessageReceived(String channel, Player player,
     * byte[] message) { if (FromBungeeDaemon.instance != null) {
     * FromBungeeDaemon.trigger(channel, player, message); } }
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("chatdirectorlocal") && sender.hasPermission("chatdirector.reload")) {
            new Thread(new TimedLoad()).start();
        }
        ((BukkitCommandDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitCommandDaemon.class)).execute(sender,
                command, label, args);
        return false;
    }

    public static ChatDirectorBukkit getInstance() {
        return instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        ((BukkitInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onPlayerChat(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        ((BukkitInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onLogin(event);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        ((BukkitInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(BukkitInputDaemon.class)).onLogout(event);
    }
}