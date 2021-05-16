package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ItemDaemon;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeInputItemDaemon extends ItemDaemon implements Listener {

    private Set<UUID> existing_players = new HashSet<>();

    @EventHandler
    public void onEvent(PlayerDisconnectEvent e) {
        Context context = ChatDirector.getConfig().getModule(BungeeModule.class).getContext(e);
        existing_players.remove(e.getPlayer().getUniqueId());
        for (BungeeInputItem item : getItems().toArray(new BungeeInputItem[] {})) {
            if (item.logout) {
                context.put("CURRENT", ChatDirector.format(item.formatLogout, context));
                ChatDirector.run(item, context, true);
            }
        }
    }

    @EventHandler
    public void onEvent(ServerSwitchEvent e) {
        Context context = ChatDirector.getConfig().getModule(BungeeModule.class).getContext(e);
        // this is needed as ServerConnectEvent is also called for the first time.
        for (BungeeInputItem item : getItems().toArray(new BungeeInputItem[] {})) {
            if (existing_players.contains(e.getPlayer().getUniqueId())) {
                if (item.switchServers) {
                    context.put("CURRENT", ChatDirector.format(item.formatSwitchServers, context));
                    ChatDirector.run(item, context, true);
                }
            } else {
                existing_players.add(e.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onEvent(ServerConnectedEvent e) {
        Context context = ChatDirector.getConfig().getModule(BungeeModule.class).getContext(e);
        // this is needed as ServerConnectEvent is also called for the first time.
        for (BungeeInputItem item : getItems().toArray(new BungeeInputItem[] {})) {
            if (!existing_players.contains(e.getPlayer().getUniqueId())) {
                if (item.login) {
                    context.put("CURRENT", ChatDirector.format(item.formatJoin, context));
                    ChatDirector.run(item, context, true);
                }
            }
        }
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        Context context = ChatDirector.getConfig().getModule(BungeeModule.class).getContext(e);
        for (BungeeInputItem item : getItems().toArray(new BungeeInputItem[] {})) {
            if ((item.chat && !e.getMessage().startsWith("/")) || item.command && e.getMessage().startsWith("/")) {
                if (item.overrideChat) {
                    context.put("CURRENT", ChatDirector.format(item.formatJoin, context));
                    e.setMessage(ChatDirector.run(item, context, false).getCurrent());
                } else {
                    context.put("CURRENT", ChatDirector.format(item.formatChat, context));
                    ChatDirector.run(item, context, true);
                }
            }
        }
    }
    /*
     * @EventHandler public void onMessage(PluginMessageEvent e) { // TODO } /*
     * 
     * @Override public boolean load() {
     * ChatDirectorBungee.instance.getProxy().getPluginManager().registerListener(
     * ChatDirectorBungee.instance, this); }
     * 
     * @Override public boolean unload() {
     * ChatDirectorBungee.instance.getProxy().getPluginManager().unregisterListener(
     * this); }
     */
}
