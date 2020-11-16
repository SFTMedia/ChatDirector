package com.blalp.chatdirector.modules.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class SpongeInputItem extends PassItem {
    public boolean chat=false,checkCanceled=false,join=false,leave=false,serverStarted=false,serverStopped=false,cancelChat=false;
    public String formatChat="%PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatLogin = "**%PLAYER_NAME% joined the server**";
    public String formatLogout = "**%PLAYER_NAME% joined the server**";
    public String formatStopped = "**Server Stopped**";
    public String formatStarted = "**Server Started**";
	public boolean overrideChat=false;

    @Override
    public boolean isValid() {
        if(!(ValidationUtils.anyOf(chat,join,leave,serverStarted,serverStopped))) {
            ChatDirector.logDebug("Missing a listener type chat/join/leave/start/stop.");
            return false;
        }
        return ValidationUtils.hasContent(formatChat,formatLogin,formatLogout,formatStarted,formatStopped);
    }
}
