package com.blalp.chatdirector.modules.sponge;

import com.blalp.chatdirector.modules.common.PassItem;

public class SpongeInputItem extends PassItem {
    public boolean chat=false,checkCanceled=false,join=false,leave=false,serverStarted=false,serverStopped=false,cancelChat=false;
    public String format="%PLAYER_NAME%: %CHAT_MESSAGE%";    
}
