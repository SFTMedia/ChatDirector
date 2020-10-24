package com.blalp.chatdirector.modules.bukkit;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public class BukkitFormatter extends Formatter {
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> context = new HashMap<>();
        context.put("NUM_PLAYERS",String.valueOf(Bukkit.getOnlinePlayers().size()));
        context.put("MAX_PLAYERS",String.valueOf(Bukkit.getMaxPlayers()));
        context.put("SERVER_NAME",String.valueOf(Bukkit.getServerName()));
        context.put("MOTD",String.valueOf(Bukkit.getMotd()));
        if(event instanceof PlayerEvent) {
            context.put("PLAYER_NAME", ((PlayerEvent)event).getPlayer().getDisplayName());
        }
        if(event instanceof Player) {
            context.put("PLAYER_NAME",((Player)event).getName());
            context.put("PLAYER_UUID",((Player)event).getUniqueId().toString());
        }
        return context;
    }
}
