package com.blalp.chatdirector.modules.sponge;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class SpongeOutputItem extends Item {
    public String permission,sender;
    @Override
    public String process(String string, Map<String, String> context) {
        if(permission!=null&&!permission.isEmpty()) {
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
                if(player.hasPermission(permission)) {
                    player.sendMessage(Text.of(ChatDirector.formatter.format(string, context)));
                }
            }
        } else {
            Sponge.getServer().getBroadcastChannel().send(Text.of(ChatDirector.formatter.format(string, context)));
        }
        return string;
    }
    
}
