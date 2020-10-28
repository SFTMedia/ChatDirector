package com.blalp.chatdirector.modules.bungee;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;

public class BungeeOutputPlayerItem extends Item {
    String player;
    boolean uuid;
    public BungeeOutputPlayerItem(String player,boolean uuid){
        this.player=player;
        this.uuid=uuid;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        if(uuid){
            ProxyServer.getInstance().getPlayer(UUID.fromString(player)).sendMessage(ChatDirector.formatter.format(string, context));
        } else {
            ProxyServer.getInstance().getPlayer(player).sendMessage(ChatDirector.formatter.format(string, context));
        }
        return string;
    }
    
}
