package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ItemDaemon;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
public class BukkitInputItem implements IItem {
    boolean chat = false, checkCanceled = false, login = false, logout = false, serverStarted = false,
            serverStopped = false, newPlayer = false, cancelChat = false;
    String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatLogin = "**%PLAYER_NAME% joined the server**";
    String formatNewPlayer = "**Welcome %PLAYER_NAME%!**";
    String formatLogout = "**%PLAYER_NAME% joined the server**";
    String formatStopped = "**Server Stopped**";
    String formatStarted = "**Server Started**";
    boolean overrideChat = false;

    public BukkitInputItem() {
        ((ItemDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(BukkitInputDaemon.class)).addItem(this);
    }

    @Override
    public Context process(Context context) {
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(chat, login, logout, serverStarted, serverStopped) && ValidationUtils
                .hasContent(formatChat, formatLogin, formatLogout, formatNewPlayer, formatStarted, formatStopped);
    }

}