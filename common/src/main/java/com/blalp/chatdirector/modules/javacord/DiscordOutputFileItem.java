package com.blalp.chatdirector.modules.javacord;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

/**
 * DiscordOutput
 */
public class DiscordOutputFileItem extends DiscordItem {
    String name="message";
    public DiscordOutputFileItem(String botName,String channelID) {
        super(botName,channelID);
    }
    @Override
    public String process(String string, Map<String,String> context) {
        ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_16));
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(ChatDirector.format(channelID,context)).get().asTextChannel().get().sendMessage(stream,ChatDirector.format(name,context));
        return string;
    }
}