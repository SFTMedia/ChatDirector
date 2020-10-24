package com.blalp.chatdirector.modules.discord;

import java.util.Map;

/**
 * DiscordOutput
 */
public class DiscordOutputItem extends DiscordItem {
    public DiscordOutputItem(String botName,String channelID) {
        super(botName,channelID);
    }

    private String botName;

    @Override
    public String process(String string, Map<String,String> context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(channelID).get().asServerTextChannel().get().sendMessage(string);
        return string;
    }
}