package com.blalp.chatdirector.modules.bungee;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.model.format.Formatter;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;

public class BungeeFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        context.put("NUM_PLAYERS", Integer.toString(ProxyServer.getInstance().getOnlineCount()));
        context.put("MAX_PLAYERS", Integer.toString(ProxyServer.getInstance().getConfig().getPlayerLimit()));
        if(event instanceof ProxiedPlayer){
            context.put("PLAYER_NAME", ((ProxiedPlayer)event).getDisplayName());
            context.put("PLAYER_UUID", ((ProxiedPlayer)event).getUniqueId().toString());
            if(((ProxiedPlayer)event).getServer()!=null){
                context.put("PLAYER_SERVER_NAME", ((ProxiedPlayer)event).getServer().getInfo().getName());
                context.put("PLAYER_SERVER_MOTD", ((ProxiedPlayer)event).getServer().getInfo().getMotd());
            }
        }
        if(event instanceof ServerInfo) {
            context.put("SERVER_NAME", ((ServerInfo)event).getName());
            context.put("SERVER_MOTD", ((ServerInfo)event).getMotd());
        }
        if(event instanceof PlayerDisconnectEvent){
            context.putAll(getContext(((PlayerDisconnectEvent)event).getPlayer()));
        }
        if(event instanceof ServerConnectedEvent){
            context.putAll(getContext(((ServerConnectedEvent)event).getPlayer()));
            context.putAll(getContext(((ServerConnectedEvent)event).getServer().getInfo()));
        }
        if(event instanceof ChatEvent){
            if(((ChatEvent)event).getSender() instanceof ProxiedPlayer) {
                context.putAll(getContext(((ChatEvent)event).getSender()));
            }
            context.put("CHAT_MESSAGE",((ChatEvent)event).getMessage());
        }
        if(context.containsKey("PLAYER_SERVER_NAME")){
            context.put("SERVER_NAME", context.get("PLAYER_SERVER_NAME"));
        }
        if(context.containsKey("PLAYER_SERVER_MOTD")){
            context.put("SERVER_MOTD", context.get("PLAYER_SERVER_MOTD"));
        }
        return context;
    }
    
}
