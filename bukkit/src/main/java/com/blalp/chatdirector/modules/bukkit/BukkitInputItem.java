package com.blalp.chatdirector.modules.bukkit;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class BukkitInputItem implements IItem {
    public boolean chat=false,checkCanceled=false,join=false,leave=false,serverStarted=false,serverStopped=false,newJoin=false,cancelChat=false;
    public String formatChat="%PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatLogin = "**%PLAYER_NAME% joined the server**";
    public String formatNewPlayer = "**Welcome %PLAYER_NAME%!**";
    public String formatLogout = "**%PLAYER_NAME% joined the server**";
    public String formatStopped = "**Server Stopped**";
    public String formatStarted = "**Server Started**";
	public boolean overrideChat=false;

    @Override
    public Context process(Context context) {
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(chat,join,leave,serverStarted,serverStopped)&&ValidationUtils.hasContent(formatChat,formatLogin,formatLogout,formatNewPlayer,formatStarted,formatStopped);
    }

}