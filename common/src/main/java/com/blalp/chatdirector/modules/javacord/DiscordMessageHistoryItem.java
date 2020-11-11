package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

public class DiscordMessageHistoryItem extends DiscordItem {
    public String format="%DISCORD_AUTHOR_DISPLAY_NAME% (%DISCORD_AUTHOR_ID%): %DISCORD_MESSAGE%";
    public int length = 50;
    public DiscordMessageHistoryItem(String botName,String channel) {
        super(botName);
        this.channelID=channel;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        Map<String,String> workingContext = (Map<String,String>)((HashMap<String,String>)context).clone();
        MessageSet messages = DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(ChatDirector.format(channelID, context)).get().getMessages(length).join();
        String output = "";
        for(Message message : messages) {
            workingContext.putAll(ChatDirector.getContext(message));
            output+=ChatDirector.format(format, workingContext);
        }
        return output;
    }
}
