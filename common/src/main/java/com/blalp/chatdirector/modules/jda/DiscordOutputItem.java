package com.blalp.chatdirector.modules.jda;

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
        DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(channelID).sendMessage(string);
        return string;
    }
}