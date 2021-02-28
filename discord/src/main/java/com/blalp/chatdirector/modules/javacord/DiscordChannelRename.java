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
public class DiscordChannelRename extends DiscordItem {
    String name, channel;

    @Override
    public Context process(Context context) {
        DiscordBot.get(bot).getDiscordApi()
                .getServerChannelById(ChatDirector.format(channel, context)).get().createUpdater()
                .setName(ChatDirector.format(name, context)).update();
        return new Context();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && ValidationUtils.hasContent(channel, name);
    }
}
