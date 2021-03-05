package com.blalp.chatdirector.bukkit.modules.placeholderapi;

import java.util.UUID;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIResolveItem implements IItem {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    @SuppressWarnings(value = "deprecation")
    public Context process(Context context) {
        OfflinePlayer player;
        if (context.containsKey("PLAYER_UUID")) {
            player = Bukkit.getOfflinePlayer(UUID.fromString(context.get("PLAYER_UUID")));
        } else if (context.containsKey("PLAYER_NAME")) {
            player = Bukkit.getOfflinePlayer(context.get("PLAYER_NAME"));
        } else {
            return new Context();
        }
        return new Context(PlaceholderAPI.setPlaceholders(player, context.getCurrent()));
    }

}
