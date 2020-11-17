package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DiscordGetDMChannelItem extends DiscordItem {

    String user;

    @Override
    public Context process(Context context) {
        User userObj = DiscordModule.instance.discordBots.get(bot).getDiscordApi()
                .getUserById(ChatDirector.format(user, context)).join();
        if (!userObj.getPrivateChannel().isPresent()) {
            userObj.openPrivateChannel().join();
        }
        Context output = new Context();
        output.put("DISCORD_DM_CHANNEL_ID", userObj.getPrivateChannel().get().getIdAsString());
        return output;
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(user) && super.isValid();
    }
}
