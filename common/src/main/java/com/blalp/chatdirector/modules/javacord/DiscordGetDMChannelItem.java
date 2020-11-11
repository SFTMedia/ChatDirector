package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class DiscordGetDMChannelItem extends DiscordItem {

    String userID;
    public DiscordGetDMChannelItem(String botName,String user) {
        super(botName);
        this.userID=user;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        this.context.put("DISCORD_DM_CHANNEL_ID", DiscordModule.discordBots.get(botName).getDiscordApi().getUserById(ChatDirector.format(userID, context)).join().getPrivateChannel().get().getIdAsString());
		return string;
    }
}
