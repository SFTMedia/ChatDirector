package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

public class DiscordMessageHistoryItem extends DiscordItem {
    public String format="%DISCORD_AUTHOR_DISPLAY_NAME% (%DISCORD_AUTHOR_ID%): %DISCORD_MESSAGE%";
    public int length = 50;
    String channelID;
    public DiscordMessageHistoryItem(String botName,String channel) {
        super(botName);
        this.channelID=channel;
    }
    @Override
    public Context process(Context context) {
        Context workingContext = new Context(context);
        MessageSet messages = DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(ChatDirector.format(channelID, context)).get().getMessages(length).join();
        String output = "";
        for(Message message : messages) {
            workingContext.merge(DiscordModule.instance.getContext(message));
            output+=ChatDirector.format(format, workingContext);
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(format,channelID)&&super.isValid();
    }
}
