package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class DiscordDeleteChannel extends DiscordItem {

    public DiscordDeleteChannel(String botName,String server,String category,String channel) {
        super(botName);
        this.serverID=server;
        this.categoryID=category;
        this.channelID=channel;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getServerChannelById(ChatDirector.format(channelID, context)).get().delete("Deleted by Chatdirector");
        return string;
    }
    
}
