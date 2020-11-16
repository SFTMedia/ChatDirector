package com.blalp.chatdirector.modules.javacord;

import java.util.Optional;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

public class DiscordInputDaemon extends ItemDaemon
        implements MessageCreateListener, ReactionAddListener, ReactionRemoveListener {
    private String botName;

    public DiscordInputDaemon(String botName) {
        this.botName = botName;
    }

    @Override
    public void load() {
        DiscordModule.discordBots.get(botName).getDiscordApi().addMessageCreateListener(this);
        DiscordModule.discordBots.get(botName).getDiscordApi().addReactionAddListener(this);
        DiscordModule.discordBots.get(botName).getDiscordApi().addReactionRemoveListener(this);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.message){
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channelID, item.categoryID)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        if (event.getUser().isPresent()&&event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.reactionRemove){
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channelID, item.categoryID)||sharesMessage(event.getMessage(), item.messageID)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        if (event.getUser().isPresent()&&event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.reactionAdd){
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channelID, item.categoryID)||sharesMessage(event.getMessage(), item.messageID)) {
                ChatDirector.run(item, DiscordModule.instance.getContext(event), true);
            }
        }
    }
    private boolean sharesChannelOrCategory(Channel channel,String channelID,String categoryID) {
        return channel.getIdAsString().equals(channelID)
            ||(channel.asCategorizable().isPresent()&& channel.asCategorizable().get().getCategory().isPresent()&&channel.asCategorizable().get().getCategory().get().getIdAsString().equals(categoryID));
    }
    private boolean sharesMessage(Optional<Message> message,String messageID) {
        return (message.isPresent()&&message.get().getIdAsString().equals(messageID));
    }
}