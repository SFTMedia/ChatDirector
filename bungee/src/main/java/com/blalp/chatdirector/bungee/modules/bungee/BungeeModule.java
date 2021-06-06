package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import xyz.olivermartin.multichat.bungee.events.PostGlobalChatEvent;

public class BungeeModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Context getContext(Object event) {
        Context context = new Context();
        context.put("NUM_PLAYERS", Integer.toString(ProxyServer.getInstance().getOnlineCount()));
        context.put("MAX_PLAYERS", Integer.toString(ProxyServer.getInstance().getConfig().getPlayerLimit()));
        if (event instanceof ProxiedPlayer) {
            context.put("PLAYER_NAME", ((ProxiedPlayer) event).getName());
            context.put("PLAYER_DISPLAYNAME", ((ProxiedPlayer) event).getDisplayName());
            context.put("PLAYER_UUID", ((ProxiedPlayer) event).getUniqueId().toString());
            if (((ProxiedPlayer) event).getServer() != null && ((ProxiedPlayer) event).getServer().getInfo() != null) {
                context.put("PLAYER_SERVER_NAME", ((ProxiedPlayer) event).getServer().getInfo().getName());
                context.put("PLAYER_SERVER_MOTD", ((ProxiedPlayer) event).getServer().getInfo().getMotd());
            }
        }
        if (event instanceof Cancellable) {
            context.put("CANCELED", ((Cancellable) event).isCancelled() ? "1" : "0");
        }
        if (event instanceof ServerInfo) {
            context.put("SERVER_NAME", ((ServerInfo) event).getName());
            context.put("SERVER_MOTD", ((ServerInfo) event).getMotd());
        }
        if (event instanceof PlayerDisconnectEvent) {
            context.merge(getContext(((PlayerDisconnectEvent) event).getPlayer()));
        }
        if (event instanceof ServerConnectedEvent) {
            context.merge(getContext(((ServerConnectedEvent) event).getPlayer()));
            context.merge(getContext(((ServerConnectedEvent) event).getServer().getInfo()));
        }
        if (event instanceof PostGlobalChatEvent) {
            context.merge(getContext(((PostGlobalChatEvent) event).getSender()));
        }
        if (event instanceof ChatEvent) {
            if (((ChatEvent) event).getSender() instanceof ProxiedPlayer) {
                context.merge(getContext(((ChatEvent) event).getSender()));
            }
            context.put("CHAT_MESSAGE", ((ChatEvent) event).getMessage());
        }
        if (event instanceof ServerSwitchEvent) {
            if (((ServerSwitchEvent) event).getFrom() != null) {
                context.merge(getContext(((ServerSwitchEvent) event).getFrom()));
            }
            if (((ServerSwitchEvent) event).getPlayer() != null) {
                context.merge(getContext(((ServerSwitchEvent) event).getPlayer()));
            }
        }
        if (context.containsKey("PLAYER_SERVER_NAME")) {
            context.put("SERVER_NAME", context.get("PLAYER_SERVER_NAME"));
        }
        if (context.containsKey("PLAYER_SERVER_MOTD")) {
            context.put("SERVER_MOTD", context.get("PLAYER_SERVER_MOTD"));
        }
        return context;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("bungee-command", "bungee-playerlist", "bungee-output", "bungee-input",
                "bungee-output-player", "bungee-output-server", "bungee-output-fancy");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "bungee-playerlist":
            return BungeePlayerlistItem.class;
        case "bungee-command":
            return BungeeCommandItem.class;
        case "bungee-output":
            return BungeeOutputItem.class;
        case "bungee-output-fancy":
            return BungeeOutputFancyItem.class;
        case "bungee-output-player":
            return BungeeOutputPlayerItem.class;
        case "bungee-output-server":
            return BungeeOutputServerItem.class;
        case "bungee-input":
            return BungeeInputItem.class;
        default:
            return null;
        }
    }
}
