package com.blalp.chatdirector.discord.modules.discord;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
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
public class DiscordOutputReactionItem extends DiscordItem {
    String emoji, channel, message;
    boolean add;

    @Override
    public Context process(Context context) {
        DiscordApi api = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                .getDiscordApi();
        Message messageObj = api.getMessageById(ChatDirector.format(message, context),
                api.getChannelById(ChatDirector.format(channel, context)).get().asTextChannel().get()).join();
        if (add) {
            if (api.getCustomEmojiById(ChatDirector.format(emoji, context)).isPresent()) {
                messageObj.addReaction(api.getCustomEmojiById(ChatDirector.format(emoji, context)).get());
            } else {
                messageObj.addReaction(ChatDirector.format(emoji, context));
            }
        } else {
            Reaction reaction;
            if (api.getCustomEmojiById(ChatDirector.format(emoji, context)).isPresent()) {
                reaction = messageObj
                        .getReactionByEmoji(api.getCustomEmojiById(ChatDirector.format(emoji, context)).get()).get();
            } else {
                reaction = messageObj.getReactionByEmoji(ChatDirector.format(emoji, context)).get();
            }
            for (User user : reaction.getUsers().join()) {
                if (!user.isYourself()) {
                    reaction.removeUser(user);
                }
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(emoji, channel, message) && super.isValid();
    }
}