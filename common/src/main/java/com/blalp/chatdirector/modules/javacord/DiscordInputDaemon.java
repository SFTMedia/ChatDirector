package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

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
            if (event.getChannel().getIdAsString().equals(item.channelID)
                || (event.getChannel().asCategorizable().isPresent()
                        && event.getChannel().asCategorizable().get().getCategory().isPresent()
                        && event.getChannel().asCategorizable().get().getCategory().get().getIdAsString().equals(item.categoryID))
                ) {
                item.startWork(ChatDirector.format(item.format, ChatDirector.formatter.getContext(event)), false,
                        ChatDirector.formatter.getContext(event));
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
            if (event.getChannel().getIdAsString().equals(item.channelID)
                || (event.getChannel().asCategorizable().isPresent()&& event.getChannel().asCategorizable().get().getCategory().isPresent()
                    && event.getChannel().asCategorizable().get().getCategory().get().getIdAsString().equals(item.categoryID))
                || (event.getMessage().isPresent()&&event.getMessage().get().getIdAsString().equals(item.messageID))
                ) {
                item.startWork(ChatDirector.format(item.format, ChatDirector.formatter.getContext(event)), false,
                        ChatDirector.formatter.getContext(event));
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
            if (event.getChannel().getIdAsString().equals(item.channelID)
                || (event.getChannel().asCategorizable().isPresent()&& event.getChannel().asCategorizable().get().getCategory().isPresent()
                        && event.getChannel().asCategorizable().get().getCategory().get().getIdAsString().equals(item.categoryID))
                || (event.getMessage().isPresent()&&event.getMessage().get().getIdAsString().equals(item.messageID))
                ) {
                item.startWork(ChatDirector.format(item.format, ChatDirector.formatter.getContext(event)), false,
                        ChatDirector.formatter.getContext(event));
            }
        }
    }
}