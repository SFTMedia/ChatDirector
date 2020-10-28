package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.modules.common.PassItem;
public class DiscordItem extends PassItem {
    public String botName;
    public String channelID;
	public String serverID;
	public String format="[%DISCORD_AUTHOR_ROLE%]%DISCORD_AUTHOR_NICK_NAME%: %DISCORD_MESSAGE%";
	
	public DiscordItem(String botName, String channelID, String serverID){
		this.botName=botName;
		this.channelID=channelID;
		this.serverID=serverID;
	}
	
	public DiscordItem(String botName, String channelID){
		this.botName=botName;
		this.channelID=channelID;
	}
	public DiscordItem(String botName){
		this.botName=botName;
	}

	public String getChannelID() {
		return channelID;
	}
}