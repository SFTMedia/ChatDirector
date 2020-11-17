package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
public class BukkitInputItem implements IItem {
    public boolean chat = false, checkCanceled = false, join = false, leave = false, serverStarted = false,
            serverStopped = false, newJoin = false, cancelChat = false;
    public String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatLogin = "**%PLAYER_NAME% joined the server**";
    public String formatNewPlayer = "**Welcome %PLAYER_NAME%!**";
    public String formatLogout = "**%PLAYER_NAME% joined the server**";
    public String formatStopped = "**Server Stopped**";
    public String formatStarted = "**Server Started**";
    public boolean overrideChat = false;

    public BukkitInputItem() {
        if (BukkitInputDaemon.instance == null) {
            BukkitInputDaemon.instance = new BukkitInputDaemon();
            ;
        }
        BukkitInputDaemon.instance.addItem(this);
    }

    @Override
    public Context process(Context context) {
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(chat, join, leave, serverStarted, serverStopped) && ValidationUtils
                .hasContent(formatChat, formatLogin, formatLogout, formatNewPlayer, formatStarted, formatStopped);
    }

}