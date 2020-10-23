package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

import org.bukkit.Bukkit;

public class BukkitOutputItem extends Item {
    public String permission;

    public BukkitOutputItem(String permission){
        this.permission=permission;
    }

    @Override
    public String process(String string, Map<String,String> context) {
        if (permission.equals("")) {
            Bukkit.broadcastMessage(string);
        } else {
            Bukkit.broadcast(string, permission);
        }
        return string;
    }

}