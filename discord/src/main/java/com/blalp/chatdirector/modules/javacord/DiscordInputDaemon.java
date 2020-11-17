package com.blalp.chatdirector.modules.javacord;

import java.util.Optional;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.utils.ItemDaemon;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DiscordInputDaemon extends ItemDaemon
        implements MessageCreateListener, ReactionAddListener, ReactionRemoveListener {
    private String bot;

    public DiscordInputDaemon(String botName) {
        this.bot = botName;
    }

    @Override
    public boolean load() {
        DiscordModule.instance.discordBots.get(bot).getDiscordApi().addMessageCreateListener(this);
        DiscordModule.instance.discordBots.get(bot).getDiscordApi().addReactionAddListener(this);
        DiscordModule.instance.discordBots.get(bot).getDiscordApi().addReactionRemoveListener(this);
        return true;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if (!item.messageEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        if (event.getUser().isPresent() && event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if (!item.reactionRemoveEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)
                    || sharesMessage(event.getMessage(), item.message)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        if (event.getUser().isPresent() && event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if (!item.reactionAddEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)
                    || sharesMessage(event.getMessage(), item.message)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }

    private boolean sharesChannelOrCategory(Channel channel, String channelID, String categoryID) {
        return channel.getIdAsString().equals(channelID)
                || (channel.asCategorizable().isPresent() && channel.asCategorizable().get().getCategory().isPresent()
                        && channel.asCategorizable().get().getCategory().get().getIdAsString().equals(categoryID));
    }

    private boolean sharesMessage(Optional<Message> message, String messageID) {
        return (message.isPresent() && message.get().getIdAsString().equals(messageID));
    }
}