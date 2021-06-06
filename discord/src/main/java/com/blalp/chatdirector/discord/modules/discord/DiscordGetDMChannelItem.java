package com.blalp.chatdirector.discord.modules.discord;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordGetDMChannelItem extends DiscordItem {

    String user;

    @Override
    public Context process(Context context) {
        User userObj = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                .getDiscordApi().getUserById(ChatDirector.format(user, context)).join();
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
