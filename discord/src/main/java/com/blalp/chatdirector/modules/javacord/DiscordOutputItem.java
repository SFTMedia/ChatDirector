package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscordOutputItem extends DiscordItem {
    String channel;
    String format = "%CURRENT%";

    @Override
    public Context process(Context context) {
        ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot).getDiscordApi()
                .getChannelById(ChatDirector.format(channel, context)).get().asTextChannel().get()
                .sendMessage(ChatDirector.format(format, context));
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channel) && super.isValid();
    }
}