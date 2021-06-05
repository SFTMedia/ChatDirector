package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.UUID;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import net.md_5.bungee.api.ProxyServer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BungeeOutputPlayerItem implements IItem {
    String player;
    String permission;

    public BungeeOutputPlayerItem(String player) {
        this.player = player;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Context process(Context context) {
        if (permission != null) {
            if (ChatDirector.format(player, context).length() > 16) {
                if (!ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.format(player, context)))
                        .hasPermission(ChatDirector.format(permission, context))) {
                    return new Context();
                }
            } else {
                if (!ProxyServer.getInstance().getPlayer(ChatDirector.format(player, context))
                        .hasPermission(ChatDirector.format(permission, context))) {
                    return new Context();
                }
            }
        }
        if (ChatDirector.format(player, context).length() > 16) {
            ProxyServer.getInstance().getPlayer(UUID.fromString(ChatDirector.format(player, context)))
                    .sendMessage(ChatDirector.format(context));
        } else {
            ProxyServer.getInstance().getPlayer(ChatDirector.format(player, context))
                    .sendMessage(ChatDirector.format(context));
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(player);
    }

}
