package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

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

public class BukkitInputDaemon extends ItemDaemon implements Listener {
    public static BukkitInputDaemon instance;
    public String format = "**%PLAYER_NAME% joined the server**";
    public String newPlayerFormat = "Welcome %PLAYER_NAME%!";

    public BukkitInputDaemon(){
        instance=this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[]{})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.chat) {
                Map<String,String> context = ChatDirector.formatter.getContext(event);
                item.startWork(ChatDirector.formatter.format(item.format,context),false,context);
            }
            if(item.cancelChat){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        Map<String,String> context = ChatDirector.formatter.getContext(event);
        String message = ChatDirector.formatter.format(format,context);
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[]{})) {
            if (event.getResult().equals(Result.ALLOWED) && item.checkCanceled) {
                continue;
            }
            if (item.join) {
                if(!event.getPlayer().hasPlayedBefore()&&item.newJoin){
                    item.startWork(ChatDirector.formatter.format(newPlayerFormat,context),true,ChatDirector.formatter.getContext(event));
                } else {
                    item.startWork(message,true,ChatDirector.formatter.getContext(event));
                }
            }
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        String message = "**" + event.getPlayer().getDisplayName() + " left the server**";
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[]{})) {
            if (item.leave) {
                item.startWork(message,true,ChatDirector.formatter.getContext(event));
            }
        }
    }

    public void onServerStart() {
        // Loaded the main world. Server started!
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[]{})) {
            if (item.serverStarted) {
                item.startWork("**Server Started**",true,ChatDirector.formatter.getContext(null));
            }
        }
    }

    public void onServerStop() {
        // Loaded the main world. Server started!
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[]{})) {
            if (item.serverStopped) {
                item.startWork("**Server Stopped**",true,ChatDirector.formatter.getContext(null));
            }
        }
    }
}