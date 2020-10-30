package com.blalp.chatdirector.modules.bungee;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeOutputServerItem extends Item {
    public String server;
    public String permission;
    public BungeeOutputServerItem(String server){
        this.server=server;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        for(ProxiedPlayer player:ProxyServer.getInstance().getPlayers()) {
            if(player.getServer()!=null&&player.getServer().getInfo()!=null&&player.getServer().getInfo().getName().equals(ChatDirector.formatter.format(server, context))){
                if(permission==null||(permission!=null&player.hasPermission(ChatDirector.formatter.format(permission, context)))){
                    player.sendMessage(ChatDirector.formatter.format(string, context));
                }
            }
        }
        return string;
    }
    
}
