package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

/**
 * DiscordOutput
 */
public class DiscordOutputItem extends DiscordItem {
    public DiscordOutputItem(String botName,String channelID) {
        super(botName,channelID);
    }
    @Override
    public String process(String string, Map<String,String> context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(ChatDirector.format(channelID,context)).get().asTextChannel().get().sendMessage(string);
        return string;
    }
}