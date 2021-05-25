package com.blalp.chatdirector.modules.javacord;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.message.MessageBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscordOutputFileItem extends DiscordItem {
    String name = "message", channel;

    @Override
    public Context process(Context context) {
        ByteArrayInputStream stream = new ByteArrayInputStream(context.getCurrent().getBytes(StandardCharsets.UTF_8));
        new MessageBuilder().append(ChatDirector.format(name, context))
                .addAttachment(stream, ChatDirector.format(name, context))
                .send(((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                        .getDiscordApi().getChannelById(ChatDirector.format(channel, context)).get().asTextChannel()
                        .get());
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(name, channel) && super.isValid();
    }
}