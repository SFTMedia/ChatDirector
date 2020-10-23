package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPlayerlistItem extends Item {
    public String triggerWord = "playerlist";
    public boolean ignoreCase = true;
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    @Override
    public String process(String string, Map<String,String> context) {
        context.putAll(ChatDirector.formatter.getContext(Bukkit.getServer()));
        if (!((string.equalsIgnoreCase(triggerWord)&&ignoreCase)||(string.equals(triggerWord)))) {
            return string;
        }
        String output = ChatDirector.formatter.format(format,context);
        boolean first = true;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!first) {
                output += ", ";
            } else {
                first = false;
            }
            output += player.getName();
        }
        output += "\n```";
        if (output.equals(ChatDirector.formatter.format(format.replace("%NUM_PLAYERS%", "0"),context))) {
            output = ChatDirector.formatter.format(formatNoPlayers,context);
        }
        return output;
    }
}