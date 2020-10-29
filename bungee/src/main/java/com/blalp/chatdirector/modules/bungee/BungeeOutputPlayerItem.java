package com.blalp.chatdirector.modules.bungee;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;

public class BungeeOutputPlayerItem extends Item {
    String player;
    String permission;
    public BungeeOutputPlayerItem(String player){
        this.player=player;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        if(permission!=null){
            if(ChatDirector.formatter.format(player, context).length()>16){
                if(!ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.formatter.format(player, context))).hasPermission(ChatDirector.formatter.format(permission, context))){
                    return string;
                }
            } else {
                if(!ProxyServer.getInstance().getPlayer(ChatDirector.formatter.format(player, context)).hasPermission(ChatDirector.formatter.format(permission, context))){
                    return string;
                }
            }
        }
        if(ChatDirector.formatter.format(player, context).length()>16){
            ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.formatter.format(player, context))).sendMessage(ChatDirector.formatter.format(string, context));
        } else {
            ProxyServer.getInstance().getPlayer(ChatDirector.formatter.format(player, context)).sendMessage(ChatDirector.formatter.format(string, context));
        }
        return string;
    }
    
}
