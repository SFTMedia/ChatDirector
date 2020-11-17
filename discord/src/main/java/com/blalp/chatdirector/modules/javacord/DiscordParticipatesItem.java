package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
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
public class DiscordParticipatesItem extends DiscordItem {
    public int length = 50;
    Chain each;
    String channel;

    @Override
    public Context process(Context context) {
        MessageSet messages = DiscordModule.instance.discordBots.get(bot).getDiscordApi()
                .getTextChannelById(ChatDirector.format(channel, context)).get().getMessages(length).join();
        HashMap<String, User> users = new HashMap<>();
        for (Message message : messages) {
            if (message.getUserAuthor().isPresent() && !message.getAuthor().isYourself()) {
                users.put(message.getUserAuthor().get().getIdAsString(), message.getUserAuthor().get());
            }
        }
        Context workingContext = new Context();
        for (User user : users.values()) {
            ChatDirector.logDebug("Starting work for " + user.getName() + " (" + user.getIdAsString() + ")");
            workingContext.merge(DiscordModule.instance.getContext(user)); // All keys should be the same for each user
                                                                           // and get overridden.
            each.run(workingContext);
            workingContext = new Context();
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return each != null && ValidationUtils.hasContent(channel) && super.isValid();
    }
}
