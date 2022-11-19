package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
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
public class DiscordDeleteChannel extends DiscordItem {

    private String channel;

    @Override
    public Context process(Context context) {
        ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot).getDiscordApi()
                .getServerChannelById(ChatDirector.format(channel, context)).get().delete("Deleted by Chatdirector");
        return new Context();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && ValidationUtils.hasContent(channel);
    }

}
