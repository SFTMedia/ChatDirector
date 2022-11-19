package com.blalp.chatdirector.modules.discord;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.entity.message.MessageBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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