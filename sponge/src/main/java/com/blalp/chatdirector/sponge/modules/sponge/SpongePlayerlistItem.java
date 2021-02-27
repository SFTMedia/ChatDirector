package com.blalp.chatdirector.sponge.modules.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class SpongePlayerlistItem implements IItem {
    boolean ignoreCase = true;
    String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    String formatNoPlayers = "**No online players**";
    String formatPlayer = "%PLAYER_NAME%";

    @Override
    public Context process(Context context) {
        String output = ChatDirector.format(format, context);
        boolean first = true;
        Context workingContext = new Context(context);
        for (Player player : Sponge.getServer().getOnlinePlayers()) {
            if (!first) {
                output += ", ";
            } else {
                first = false;
            }
            workingContext.merge(SpongeModule.instance.getContext(player));
            output += ChatDirector.format(formatPlayer, workingContext);
            workingContext = new Context(context);
        }
        output += "\n```";
        if (output.equals(ChatDirector.format(format.replace("%NUM_PLAYERS%", "0"), context))) {
            output = ChatDirector.format(formatNoPlayers, context);
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(format, formatNoPlayers, formatPlayer);
    }
}