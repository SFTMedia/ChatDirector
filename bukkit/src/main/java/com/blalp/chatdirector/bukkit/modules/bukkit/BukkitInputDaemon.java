package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ItemDaemon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class BukkitInputDaemon extends ItemDaemon implements Listener {
    public static BukkitInputDaemon instance;

    public BukkitInputDaemon() {
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[] {})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.chat) {
                Context context = BukkitModule.instance.getContext(event);
                if (item.overrideChat) {
                    context.put("CURRENT", ChatDirector.format(item.formatChat, context));
                    event.setFormat(ChatDirector.run(item, context, false).getCurrent());
                } else {
                    context.put("CURRENT", ChatDirector.format(item.formatChat, context));
                    ChatDirector.run(item, context, true);
                }
            }
            if (item.cancelChat) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        Context context = BukkitModule.instance.getContext(event);
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[] {})) {
            if (event.getResult().equals(Result.ALLOWED) && item.checkCanceled) {
                continue;
            }
            if (item.join) {
                if (!event.getPlayer().hasPlayedBefore() && item.newJoin) {
                    context.put("CURRENT", ChatDirector.format(item.formatLogin, context));
                    ChatDirector.run(item, context, true);
                } else {
                    context.put("CURRENT", ChatDirector.format(item.formatLogin, context));
                    ChatDirector.run(item, context, true);
                }
            }
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Context context = BukkitModule.instance.getContext(event);
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[] {})) {
            if (item.leave) {
                context.put("CURRENT", ChatDirector.format(item.formatLogout, context));
                ChatDirector.run(item, context, true);
            }
        }
    }

    public void onServerStart() {
        Context context = BukkitModule.instance.getContext(null);
        // Loaded the main world. Server started!
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[] {})) {
            if (item.serverStarted) {
                context.put("CURRENT", ChatDirector.format(item.formatStarted, context));
                ChatDirector.run(item, context, true);
            }
        }
    }

    public void onServerStop() {
        Context context = BukkitModule.instance.getContext(null);
        // Loaded the main world. Server started!
        for (BukkitInputItem item : items.toArray(new BukkitInputItem[] {})) {
            if (item.serverStopped) {
                context.put("CURRENT", ChatDirector.format(item.formatStopped, context));
                ChatDirector.run(item, context, true);
            }
        }
    }
}