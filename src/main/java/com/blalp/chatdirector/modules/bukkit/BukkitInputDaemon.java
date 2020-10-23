package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.ChatDirectorBukkit;
import com.blalp.chatdirector.model.ItemDaemon;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.world.WorldLoadEvent;

public class BukkitInputDaemon extends ItemDaemon implements Listener {
    public String format = "**%PLAYER_NAME% joined the server**";
    public String newPlayerFormat = "Welcome %PLAYER_NAME%!";
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        for (BukkitInputItem item : (BukkitInputItem[])items.toArray()) {
            if (event.isCancelled() && item.isCheckCanceled()) {
                continue;
            }
            if (item.isChat()) {
                item.process(ChatDirector.formatter.format(event, item.getFormat()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        String message = ChatDirector.formatter.format(event, format);
        String newPlayerMessage="";
        if(!event.getPlayer().hasPlayedBefore()){
            newPlayerMessage = ChatDirector.formatter.format(event, newPlayerFormat);
        }
        for (BukkitInputItem item : (BukkitInputItem[])items.toArray()) {
            if (event.getResult().equals(Result.ALLOWED) && item.isCheckCanceled()) {
                continue;
            }
            if (item.isJoin()) {
                if(!event.getPlayer().hasPlayedBefore()&&item.isNewJoin()){
                    item.process(newPlayerMessage);
                } else {
                    item.process(message);
                }
            }
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        String message = "**" + event.getPlayer().getDisplayName() + " left the server**";
        for (BukkitInputItem item : (BukkitInputItem[])items.toArray()) {
            if (item.isLeave()) {
                item.process(message);
            }
        }
    }

    @EventHandler
    public void onServerStart(WorldLoadEvent event) {
        if(Bukkit.getWorlds().indexOf(event.getWorld())!=0){
            return;
        }
        // Loaded the main world. Server started!
        for (BukkitInputItem item : (BukkitInputItem[])items.toArray()) {
            if (item.isServerStarted()) {
                item.process("**Server Started**");
            }
        }
    }

    @EventHandler
    public void onServerStop(WorldLoadEvent event) {
        if(Bukkit.getWorlds().indexOf(event.getWorld())!=0){
            return;
        }
        // Loaded the main world. Server started!
        for (BukkitInputItem item : (BukkitInputItem[])items.toArray()) {
            if (item.isServerStopped()) {
                item.process("**Server Stopped**");
            }
        }
    }
    @Override
    public void load() {
        Bukkit.getPluginManager().registerEvents(this, ChatDirectorBukkit.instance);
    }
}