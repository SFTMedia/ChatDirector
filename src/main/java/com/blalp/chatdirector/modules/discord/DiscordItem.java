package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.internalModules.common.PassItem;
public class DiscordItem extends PassItem {
    public String botName;
    public long channelID;
	public long serverID;
	
	public DiscordItem(String botName, String channelID, String serverID){
		this.botName=botName;
		this.channelID=Long.parseLong(channelID);
		this.serverID=Long.parseLong(serverID);
	}
	
	public DiscordItem(String botName, String channelID){
		this.botName=botName;
		this.channelID=Long.parseLong(channelID);
	}
	public DiscordItem(String botName){
		this.botName=botName;
	}

	public long getChannelID() {
		return channelID;
	}
}