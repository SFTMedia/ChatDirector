package com.blalp.chatdirector.modules.bukkit;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.bukkit.Server;
import org.bukkit.event.player.PlayerEvent;

public class BukkitFormatter extends Formatter {
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> context = new HashMap<>();
        if (event instanceof Server){
            context.put("%NUM_PLAYERS%",String.valueOf(((Server)event).getOnlinePlayers().size()));
            context.put("%MAX_PLAYERS%",String.valueOf(((Server)event).getMaxPlayers()));
        }
        if(event instanceof PlayerEvent) {
            context.put("%PLAYER_NAME%", ((PlayerEvent)event).getPlayer().getDisplayName());
        }
        return context;
    }
}
