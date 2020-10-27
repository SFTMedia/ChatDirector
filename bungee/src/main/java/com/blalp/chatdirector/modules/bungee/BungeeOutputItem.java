package com.blalp.chatdirector.modules.bungee;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;

public class BungeeOutputItem extends Item {

    @Override
    public String process(String string, Map<String, String> context) {
        ProxyServer.getInstance().broadcast(ChatDirector.formatter.format(string, context));
        return string;
    }
    
}
