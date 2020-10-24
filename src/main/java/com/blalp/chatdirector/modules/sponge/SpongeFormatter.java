package com.blalp.chatdirector.modules.sponge;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.event.user.TargetUserEvent;

public class SpongeFormatter extends Formatter {
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> context = new HashMap<>();
        context.put("SERVER_NUM_PLAYERS",String.valueOf(Sponge.getServer().getOnlinePlayers().size()));
        context.put("SERVER_MAX_PLAYERS",String.valueOf(Sponge.getServer().getMaxPlayers()));
        context.put("SERVER_MOTD",String.valueOf(Sponge.getServer().getMotd()));
        if(event instanceof Player) {
            context.put("PLAYER_NAME",((Player)event).getName());
            context.put("PLAYER_UUID",((Player)event).getUniqueId().toString());
        }
        if(event instanceof Chat) {
            context.put("CHAT_MESSAGE",((Chat)event).getMessage().toPlain());
            context.put("CHAT_FORMAT",((Chat)event).getMessage().getFormat().toString());
        }
        if(event instanceof TargetUserEvent){
            context.put("PLAYER_NAME",((TargetUserEvent)event).getTargetUser().getName());
            context.put("PLAYER_UUID",((TargetUserEvent)event).getTargetUser().getUniqueId().toString());
        }
        return context;
    }
}
