package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.internalModules.common.PassItem;
public class DiscordItem extends PassItem {
    public String botName;
    public long channelID;

	public long getChannelID() {
		return channelID;
	}
}