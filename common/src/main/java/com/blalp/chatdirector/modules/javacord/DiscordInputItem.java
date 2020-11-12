package com.blalp.chatdirector.modules.javacord;

public class DiscordInputItem extends DiscordItem {
	public boolean reactionAdd=false;
	public boolean reactionRemove=false;
	public boolean message=false;

    public DiscordInputItem(String botName) {
        super(botName);
    }

}
