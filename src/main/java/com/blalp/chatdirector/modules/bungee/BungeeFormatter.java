package com.blalp.chatdirector.modules.bungee;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;
import com.mysql.fabric.Server;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        if(event instanceof ProxiedPlayer){
            context.put("PLAYER_NAME", ((ProxiedPlayer)event).getDisplayName());
            context.put("PLAYER_UUID", ((ProxiedPlayer)event).getUniqueId().toString());
            context.put("PLAYER_SERVER_NAME", ((ProxiedPlayer)event).getServer().getInfo().getName());
            context.put("PLAYER_SERVER_MOTD", ((ProxiedPlayer)event).getServer().getInfo().getMotd());
        }
        if(event instanceof ServerInfo) {
            context.put("SERVER_NAME", ((ServerInfo)event).getName());
            context.put("SERVER_MOTD", ((ServerInfo)event).getMotd());
            context.put("SERVER_ADDRESS", ((ServerInfo)event).getSocketAddress().toString());
        }
        return null;
    }
    
}
