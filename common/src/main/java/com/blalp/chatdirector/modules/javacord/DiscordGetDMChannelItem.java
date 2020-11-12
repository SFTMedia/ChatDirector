package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

import org.javacord.api.entity.user.User;

public class DiscordGetDMChannelItem extends DiscordItem {

    String userID;
    public DiscordGetDMChannelItem(String botName,String user) {
        super(botName);
        this.userID=user;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        User user = DiscordModule.discordBots.get(botName).getDiscordApi().getUserById(ChatDirector.format(userID, context)).join();
        if(!user.getPrivateChannel().isPresent()){
            user.openPrivateChannel().join();
        }
        this.context.put("DISCORD_DM_CHANNEL_ID", user.getPrivateChannel().get().getIdAsString());
		return string;
    }
}
