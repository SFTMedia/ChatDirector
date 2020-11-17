package com.blalp.chatdirector.modules.bungee;

import java.util.Map.Entry;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.platform.bukkit.ChatDirectorBukkit;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class ToBungeeItem implements IItem {
    public String channel;
    public ToBungeeItem(String channel){
        this.channel=channel;
    }
    @Override
    public Context process(Context context) {
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
        out.writeUTF(context.getCurrent());
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(ChatDirectorBukkit.instance, "BungeeCord", out.toByteArray());
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel);
    }
}
