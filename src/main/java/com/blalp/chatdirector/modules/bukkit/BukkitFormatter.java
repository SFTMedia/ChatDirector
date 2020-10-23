package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.bukkit.Server;
import org.bukkit.event.player.PlayerEvent;

public class BukkitFormatter extends Formatter {
    public String format(Object context, String format) {
        format = super.format(context,format);
        if (context instanceof Server){
            format.replaceAll("%NUM_PLAYERS%",String.valueOf(((Server)context).getOnlinePlayers().size()));
            format.replaceAll("%MAX_PLAYERS%",String.valueOf(((Server)context).getMaxPlayers()));
        }
        if(context instanceof PlayerEvent) {
            format.replaceAll("%PLAYER_NAME%", ((PlayerEvent)context).getPlayer().getDisplayName());
        }
        return format;
    }
}
