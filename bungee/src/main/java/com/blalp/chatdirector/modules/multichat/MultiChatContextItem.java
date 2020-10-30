package com.blalp.chatdirector.modules.multichat;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.blalp.chatdirector.model.Item;

import xyz.olivermartin.multichat.bungee.PlayerMeta;
import xyz.olivermartin.multichat.bungee.PlayerMetaManager;

public class MultiChatContextItem extends Item {

    @Override
    public String process(String string, Map<String, String> context) {
        if(context.containsKey("PLAYER_UUID")){
            Optional<PlayerMeta> playerMeta = PlayerMetaManager.getInstance().getPlayer(UUID.fromString(context.get("PLAYER_UUID")));
            if(playerMeta.isPresent()){
                this.context.put("PLAYER_PREFIX", playerMeta.get().prefix);
                this.context.put("PLAYER_SUFFIX", playerMeta.get().suffix);
                this.context.put("PLAYER_NICK", playerMeta.get().nick);
                this.context.put("PLAYER_WORLD", playerMeta.get().world);
                this.context.put("PLAYER_NAME", playerMeta.get().name);
            }
        }
        return string;
    }
    
}
