package com.blalp.chatdirector.sponge.modules.sponge;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class SpongePlayerlistItem implements IItem {
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
            workingContext.merge(ChatDirector.getConfig().getModule(SpongeModule.class).getContext(player));
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