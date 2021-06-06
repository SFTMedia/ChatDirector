package com.blalp.chatdirector.discord.modules.discord;

import java.util.concurrent.ExecutionException;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.javacord.api.DiscordApi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordDeleteMessage extends DiscordItem {

    String message;
    String channel;

    @Override
    public Context process(Context context) {
        try {
            DiscordApi api = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                    .getDiscordApi();
            api.getMessageById(ChatDirector.format(message, context),
                    api.getTextChannelById(ChatDirector.format(channel, context)).get()).get().delete().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel) && ValidationUtils.hasContent(message) && super.isValid();
    }
}
