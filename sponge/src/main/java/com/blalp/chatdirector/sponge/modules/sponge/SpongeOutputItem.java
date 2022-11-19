package com.blalp.chatdirector.sponge.modules.sponge;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class SpongeOutputItem implements IItem {
    String permission;

    @Override
    public Context process(Context context) {
        if (ValidationUtils.hasContent(permission)) {
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
                if (player.hasPermission(permission)) {
                    player.sendMessage(Text.of(ChatDirector.format(context)));
                }
            }
        } else {
            Sponge.getServer().getBroadcastChannel().send(Text.of(ChatDirector.format(context)));
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
