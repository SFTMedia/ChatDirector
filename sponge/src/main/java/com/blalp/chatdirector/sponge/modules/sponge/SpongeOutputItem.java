package com.blalp.chatdirector.sponge.modules.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class SpongeOutputItem implements IItem {
    public String permission, sender;

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
        return ValidationUtils.hasContent(permission, sender);
    }

}
