package com.blalp.chatdirector.modules.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper=false)
public class SpongeInputItem extends PassItem {
    public boolean chat=false,checkCanceled=false,join=false,leave=false,serverStarted=false,serverStopped=false,cancelChat=false;
    public String formatChat="%PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatLogin = "**%PLAYER_NAME% joined the server**";
    public String formatLogout = "**%PLAYER_NAME% joined the server**";
    public String formatStopped = "**Server Stopped**";
    public String formatStarted = "**Server Started**";
	public boolean overrideChat=false;

    public SpongeInputItem(){
        if (SpongeInputDaemon.instance == null) {
            SpongeInputDaemon.instance= new SpongeInputDaemon();
        }
        SpongeInputDaemon.instance.addItem(this);
    }

    @Override
    public boolean isValid() {
        if(!(ValidationUtils.anyOf(chat,join,leave,serverStarted,serverStopped))) {
            ChatDirector.logDebug("Missing a listener type chat/join/leave/start/stop.");
            return false;
        }
        return ValidationUtils.hasContent(formatChat,formatLogin,formatLogout,formatStarted,formatStopped);
    }
}
