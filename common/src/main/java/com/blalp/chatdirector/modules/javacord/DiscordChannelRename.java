package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class DiscordChannelRename extends DiscordItem {
    String name;
    public DiscordChannelRename(String botName, String channelID,String name) {
        super(botName, channelID);
        this.name=name;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        DiscordModule.discordBots.get(botName).getDiscordApi().getServerChannelById(ChatDirector.format(channelID, context)).get().updateName(ChatDirector.format(name, context));
        return super.process(string, context);
    }
}
