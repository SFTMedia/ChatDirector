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
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().getId() == DiscordModule.discordBots.get(botName).getDiscordApi().getYourself()
                .getId()) {
            return;
        }
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.message){
                return;
            }
            if (event.getChannel().getIdAsString().equals(item.channelID)
                    || (event.getChannel().asServerChannel().isPresent()
                            && event.getChannel().asServerChannel().get().asCategorizable().isPresent()
                            && event.getChannel().asServerChannel().get().asCategorizable().get().getCategory()
                                    .isPresent()
                            && event.getChannel().asServerChannel().get().asCategorizable().get().getCategory().get()
                                    .getIdAsString().equals(item.categoryID))) {
                item.startWork(ChatDirector.format(item.format, ChatDirector.formatter.getContext(event)), false,
                        ChatDirector.formatter.getContext(event));
            }
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.reactionRemove){
                return;
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
        for (DiscordInputItem item : items.toArray(new DiscordInputItem[] {})) {
            if(!item.reactionAdd){
                return;
            }
            if(event.getServer().isPresent()&&!event.getMessage().get().getServer().get().getIdAsString().equals(item.serverID)){
                return;
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