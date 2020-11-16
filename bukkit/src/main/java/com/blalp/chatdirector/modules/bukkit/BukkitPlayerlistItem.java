package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPlayerlistItem implements IItem {
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    public String formatPlayer = "%PLAYER_NAME%";
    @Override
    public Context process(Context context) {        
        String output = ChatDirector.format(format,context);
        boolean first = true;
        Context tempContext = new Context(context);
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!first) {
                output += ", ";
            } else {
                first = false;
            }
            tempContext.merge(BukkitModule.instance.getContext(player));
            output += ChatDirector.format(formatPlayer, tempContext);
        }
        output += "\n```";
        if (output.equals(ChatDirector.format(format.replace("%NUM_PLAYERS%", "0"),context))) {
            output = ChatDirector.format(formatNoPlayers,context);
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(format,formatNoPlayers,formatPlayer);
    }
}