package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.md_5.bungee.api.plugin.Listener;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class BungeeInputItem extends PassItem implements Listener {

    String formatDisconnect = "&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%";
    String formatSwitchServers = "&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    String formatChat = "[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatJoin = "&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    boolean disconnect, switchServers, chat, join;
    boolean command = false;
    boolean overrideChat = false;

    public BungeeInputItem() {

        if (BungeeInputItemDaemon.instance == null) {
            BungeeInputItemDaemon.instance = new BungeeInputItemDaemon();
        }
        BungeeInputItemDaemon.instance.addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(disconnect, switchServers, chat, join, command)
                && ValidationUtils.hasContent(formatDisconnect, formatSwitchServers, formatChat, formatJoin);
    }
}
