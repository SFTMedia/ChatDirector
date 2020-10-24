package com.blalp.chatdirector.modules.sponge;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class SpongeFormatter extends Formatter {
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> context = new HashMap<>();
        context.put("NUM_PLAYERS",String.valueOf(Sponge.getServer().getOnlinePlayers().size()));
        context.put("MAX_PLAYERS",String.valueOf(Sponge.getServer().getMaxPlayers()));
        context.put("MOTD",String.valueOf(Sponge.getServer().getMotd()));
        if(event instanceof Player) {
            context.put("PLAYER_NAME",((Player)event).getName());
            context.put("PLAYER_UUID",((Player)event).getUniqueId().toString());
        }
        return context;
    }
}
