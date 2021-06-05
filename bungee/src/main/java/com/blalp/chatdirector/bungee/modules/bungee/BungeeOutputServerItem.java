package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BungeeOutputServerItem implements IItem {
    String server;
    String permission;

    public BungeeOutputServerItem(String server) {
        this.server = server;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Context process(Context context) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.getServer() != null && player.getServer().getInfo() != null
                    && player.getServer().getInfo().getName().equals(ChatDirector.format(server, context))) {
                if (permission == null
                        || (permission != null & player.hasPermission(ChatDirector.format(permission, context)))) {
                    player.sendMessage(ChatDirector.format(context));
                }
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(server);
    }

}
