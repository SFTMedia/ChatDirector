package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

/**
 * DiscordOutput
 */
public class DiscordOutputItem extends DiscordItem {
    public DiscordOutputItem(String botName,String channelID) {
        super(botName,channelID);
    }
    @Override
    public String process(String string, Map<String,String> context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(channelID).get().asServerTextChannel().get().sendMessage(string);
        return string;
    }
}