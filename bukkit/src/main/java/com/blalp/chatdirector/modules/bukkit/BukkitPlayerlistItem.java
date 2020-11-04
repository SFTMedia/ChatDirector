package com.blalp.chatdirector.modules.bukkit;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPlayerlistItem extends Item {
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    public String formatPlayer = "%PLAYER_NAME%";
    @Override
    public String process(String string, Map<String,String> context) {
        context.putAll(ChatDirector.formatter.getContext(Bukkit.getServer()));
        context.put("CURRENT", string);
        
        // Put it into pipe no matter what.
        
        String output = ChatDirector.format(format,context);
        boolean first = true;
        Map<String,String> tempContext = new HashMap<>();
        tempContext.putAll(context);
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!first) {
                output += ", ";
            } else {
                first = false;
            }
            tempContext.putAll(ChatDirector.formatter.getContext(player));
            output += ChatDirector.format(formatPlayer, tempContext);
            tempContext= new HashMap<>();
            tempContext.putAll(context);
        }
        output += "\n```";
        if (output.equals(ChatDirector.format(format.replace("%NUM_PLAYERS%", "0"),context))) {
            output = ChatDirector.format(formatNoPlayers,context);
        }
        return output;
    }
}