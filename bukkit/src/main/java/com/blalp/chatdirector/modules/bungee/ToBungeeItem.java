package com.blalp.chatdirector.modules.bungee;

import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirectorBukkit;
import com.blalp.chatdirector.model.Item;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ToBungeeItem extends Item {
    public String channel;
    public ToBungeeItem(String channel){
        this.channel=channel;
    }
    @Override
    public String process(String string, Map<String,String> context) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL"); // Should this be all or online https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/#forward?
        out.writeUTF("ChatDirector");
        out.writeUTF(channel);
        out.writeInt(context.size());
        for (Entry<String,String> contextItem : context.entrySet()) {
            out.writeUTF(contextItem.getKey());
            out.writeUTF(contextItem.getValue());
        }
        out.writeUTF(string);
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(ChatDirectorBukkit.instance, "BungeeCord", out.toByteArray());
        return string;
    }
}
