package com.blalp.chatdirector.modules.javacord;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.message.MessageBuilder;

/**
 * DiscordOutput
 */
public class DiscordOutputFileItem extends DiscordItem {
    String name="message",channelID;
    public DiscordOutputFileItem(String botName,String channelID) {
        super(botName);
    }
    @Override
    public Context process(Context context) {
        ByteArrayInputStream stream = new ByteArrayInputStream(context.getCurrent().getBytes(StandardCharsets.UTF_16));
        new MessageBuilder().append(ChatDirector.format(name,context)).addAttachment(stream,ChatDirector.format(name,context)).send(DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(ChatDirector.format(channelID,context)).get().asTextChannel().get());
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(name,channelID)&&super.isValid();
    }
}