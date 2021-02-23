package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

import org.bukkit.Bukkit;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BukkitOutputItem implements IItem {
    public String permission = null;

    @Override
    public Context process(Context context) {
        if (permission == null) {
            Bukkit.broadcastMessage(ChatDirector.format(context));
        } else {
            Bukkit.broadcast(ChatDirector.format(context), permission);
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return true;
    }

}