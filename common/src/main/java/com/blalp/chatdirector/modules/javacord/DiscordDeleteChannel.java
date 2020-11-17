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
@EqualsAndHashCode(callSuper=false)
public class DiscordDeleteChannel extends DiscordItem {

    private String channel;

    @Override
    public Context process(Context context) {
        DiscordModule.instance.discordBots.get(bot).getDiscordApi().getServerChannelById(ChatDirector.format(channel, context)).get().delete("Deleted by Chatdirector");
        return new Context();
    }
    @Override
    public boolean isValid() {
        return super.isValid()&&ValidationUtils.hasContent(channel);
    }
    
}
