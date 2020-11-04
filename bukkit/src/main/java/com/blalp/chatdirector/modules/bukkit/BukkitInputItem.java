package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class BukkitInputItem extends Item {
    public boolean chat=false,checkCanceled=false,join=false,leave=false,serverStarted=false,serverStopped=false,newJoin=false,cancelChat=false;
    public String format="%PLAYER_NAME%: %CHAT_MESSAGE%";
	public boolean overrideChat;

    @Override
    public String process(String string, Map<String,String> context) {
        return string;
    }

}