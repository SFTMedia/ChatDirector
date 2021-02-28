package com.blalp.chatdirector.bukkit.modules.bungeeMessage;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class ToBungeeItem implements IItem {
    String channel;

    public ToBungeeItem(String channel) {
        this.channel = channel;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Context process(Context context) {
        return null;
    }
/*
    @Override
    public Context process(Context context) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        // Should this be all or online
        // https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/#forward?
        out.writeUTF("ALL");
        out.writeUTF("ChatDirector");
        out.writeUTF(channel);
        out.writeInt(context.size());
        for (Entry<String, String> contextItem : context.entrySet()) {
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
    */
}
