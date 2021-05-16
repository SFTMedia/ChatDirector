package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.ChatDirector;
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

    String formatLogout = "&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%";
    String formatSwitchServers = "&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    String formatChat = "[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatJoin = "&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    boolean logout, switchServers, chat, login;
    boolean command = false;
    boolean overrideChat = false;

    public BungeeInputItem() {
        ChatDirector.getConfig().getOrCreateDaemon(BungeeInputItemDaemon.class).addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(logout, switchServers, chat, login, command)
                && ValidationUtils.hasContent(formatLogout, formatSwitchServers, formatChat, formatJoin);
    }
}
