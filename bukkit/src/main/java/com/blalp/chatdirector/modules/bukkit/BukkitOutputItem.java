package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import org.bukkit.Bukkit;

public class BukkitOutputItem extends Item {
    public String permission=null;

    @Override
    public String process(String string, Map<String,String> context) {
        if (permission==null) {
            Bukkit.broadcastMessage(ChatDirector.format(string, context));
        } else {
            Bukkit.broadcast(ChatDirector.format(string, context), permission);
        }
        return string;
    }

}