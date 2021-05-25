package com.blalp.chatdirector.sponge.modules.sponge;

import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ItemDaemon;
import com.blalp.chatdirector.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class SpongeInputItem extends PassItem {
    boolean chat = false, checkCanceled = false, login = false, logout = false, serverStarted = false,
            serverStopped = false, cancelChat = false, overrideChat = false;
    String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatLogin = "**%PLAYER_NAME% joined the server**";
    String formatLogout = "**%PLAYER_NAME% joined the server**";
    String formatStopped = "**Server Stopped**";
    String formatStarted = "**Server Started**";

    public SpongeInputItem() {
        ((ItemDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(SpongeInputDaemon.class)).addItem(this);
    }

    @Override
    public boolean isValid() {
        if (!(ValidationUtils.anyOf(chat, login, logout, serverStarted, serverStopped))) {
            ChatDirector.getLogger().log(Level.WARNING, "Missing a listener type chat/join/leave/start/stop.");
            return false;
        }
        return ValidationUtils.hasContent(formatChat, formatLogin, formatLogout, formatStarted, formatStopped);
    }
}
