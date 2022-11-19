package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import org.bukkit.Bukkit;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BukkitOutputItem implements IItem {
    String permission = null;

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