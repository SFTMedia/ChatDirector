package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitContextItem implements IItem {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context process(Context context) {
        Context output = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(null);
        for (Player player : Bukkit.getOnlinePlayers()) {
            output.put("PLAYER_NAME_"+player.getUniqueId().toString(), player.getName());
            output.put("PLAYER_LOCATION_"+player.getUniqueId().toString(), player.getLocation().toVector().toString());
            output.put("PLAYER_WORLD_"+player.getUniqueId().toString(), player.getLocation().getWorld().getName());
        }
        return output;
    }
    
}
