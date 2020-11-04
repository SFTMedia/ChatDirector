package com.blalp.chatdirector.modules.bungee;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;

public class BungeePlayerItem extends Item {
    String player;
    String permission;
    public BungeePlayerItem(String player){
        this.player=player;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        if(permission!=null){
            if(ChatDirector.format(player, context).length()>16){
                if(!ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.format(player, context))).hasPermission(ChatDirector.format(permission, context))){
                    return string;
                }
            } else {
                if(!ProxyServer.getInstance().getPlayer(ChatDirector.format(player, context)).hasPermission(ChatDirector.format(permission, context))){
                    return string;
                }
            }
        }
        if(ChatDirector.format(player, context).length()>16){
            ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.format(player, context))).sendMessage(ChatDirector.format(string, context));
        } else {
            ProxyServer.getInstance().getPlayer(ChatDirector.format(player, context)).sendMessage(ChatDirector.format(string, context));
        }
        return string;
    }
    
}
