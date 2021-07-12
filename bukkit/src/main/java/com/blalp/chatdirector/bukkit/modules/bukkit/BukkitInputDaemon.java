package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ItemDaemon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BukkitInputDaemon extends ItemDaemon {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        for (BukkitInputItem item : getItems().toArray(new BukkitInputItem[] {})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.chat) {
                Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(event);
                if (item.overrideChat) {
                    context.put("CURRENT", ChatDirector.format(item.formatChat, context));
                    event.setFormat(ChatDirector.run(item, context, false).getCurrent().replace("%", "%%"));
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
        Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(event);
        for (BukkitInputItem item : getItems().toArray(new BukkitInputItem[] {})) {
            if (event.getResult().equals(Result.ALLOWED) && item.checkCanceled) {
                continue;
            }
            if (item.login) {
                if (!event.getPlayer().hasPlayedBefore() && item.newPlayer) {
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
        Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(event);
        for (BukkitInputItem item : getItems().toArray(new BukkitInputItem[] {})) {
            if (item.logout) {
                context.put("CURRENT", ChatDirector.format(item.formatLogout, context));
                ChatDirector.run(item, context, true);
            }
        }
    }

    public void onServerStart() {
        Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(null);
        // Loaded the main world. Server started!
        for (BukkitInputItem item : getItems().toArray(new BukkitInputItem[] {})) {
            if (item.serverStarted) {
                context.put("CURRENT", ChatDirector.format(item.formatStarted, context));
                ChatDirector.run(item, context, true);
            }
        }
    }

    public void onServerStop() {
        Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(null);
        // Loaded the main world. Server started!
        for (BukkitInputItem item : getItems().toArray(new BukkitInputItem[] {})) {
            if (item.serverStopped) {
                context.put("CURRENT", ChatDirector.format(item.formatStopped, context));
                ChatDirector.run(item, context, true);
            }
        }
    }
}