package com.blalp.chatdirector.modules.sponge;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class SpongePlayerlistItem extends Item {
    public String triggerWord = "playerlist";
    public boolean ignoreCase = true;
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    public String formatPlayer = "%PLAYER_NAME%";
    @Override
    public String process(String string, Map<String,String> context) {
        context.putAll(ChatDirector.formatter.getContext(Sponge.getServer()));
        if (!((string.equalsIgnoreCase(triggerWord)&&ignoreCase)||(string.equals(triggerWord)))) {
            return string;
        }
        String output = ChatDirector.formatter.format(format,context);
        boolean first = true;
        Map<String,String> tempContext = new HashMap<>();
        tempContext.putAll(context);
        for (Player player : Sponge.getServer().getOnlinePlayers()) {
            if (!first) {
                output += ", ";
            } else {
                first = false;
            }
            tempContext.putAll(ChatDirector.formatter.getContext(player));
            output += ChatDirector.formatter.format(formatPlayer, tempContext);
            tempContext= new HashMap<>();
            tempContext.putAll(context);
        }
        output += "\n```";
        if (output.equals(ChatDirector.formatter.format(format.replace("%NUM_PLAYERS%", "0"),context))) {
            output = ChatDirector.formatter.format(formatNoPlayers,context);
        }
        return output;
    }
}