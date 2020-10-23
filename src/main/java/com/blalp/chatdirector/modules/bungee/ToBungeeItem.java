package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.ChatDirectorBukkit;
import com.blalp.chatdirector.model.Item;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ToBungeeItem extends Item {
    public String channel;
    @Override
    public String process(String string) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL"); // Should this be all or online https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/#forward?
        out.writeUTF("ChatDirector");
        out.writeUTF(channel);
        out.writeUTF(string);
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(ChatDirectorBukkit.instance, "BungeeCord", out.toByteArray());
        return string;
    }
}
