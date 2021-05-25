package com.blalp.chatdirector.modules.javacord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.utils.ItemDaemon;

import org.javacord.api.DiscordApi;
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

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscordInputDaemon
        implements MessageCreateListener, ReactionAddListener, ReactionRemoveListener, IDaemon {

    // ONLY used per-load.
    HashMap<String,List<DiscordInputItem>> pendingItems = new HashMap<>();
    // constructed from pendingItems during load
    HashMap<DiscordApi,List<DiscordInputItem>> items = new HashMap<>();

    @Override
    public boolean load() {
        DiscordBots discordBots = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class));
        for (Entry<String,List<DiscordInputItem>> entry : pendingItems.entrySet()) {
            items.put(discordBots.get(entry.getKey()).getDiscordApi(), entry.getValue());
        }
        pendingItems=new HashMap<>();
        // Make sure discord bots are already loaded before continuing.
        discordBots.load();
        for (DiscordBot discordBot : discordBots.values()) {
            discordBot.getDiscordApi().addMessageCreateListener(this);
            discordBot.getDiscordApi().addReactionAddListener(this);
            discordBot.getDiscordApi().addReactionRemoveListener(this);
        }
        return true;
    }

    @Override
    public boolean unload() {
        items=new HashMap<>();
        pendingItems=new HashMap<>();
        return true;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().isYourself()) {
            return;
        }
        for (DiscordInputItem item : getItems().get(event.getApi()).toArray(new DiscordInputItem[] {})) {
            if (!item.messageEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)) {
                ChatDirector.run(item,
                        ((DiscordModule) ChatDirector.getConfig().getModule(DiscordModule.class)).getContext(event),
                        true);
            }
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        if (event.getUser().isPresent() && event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : getItems().get(event.getApi()).toArray(new DiscordInputItem[] {})) {
            if (!item.reactionRemoveEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)
                    || sharesMessage(event.getMessage(), item.message)) {
                ChatDirector.run(item,
                        ((DiscordModule) ChatDirector.getConfig().getModule(DiscordModule.class)).getContext(event),
                        true);
            }
        }
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        if (event.getUser().isPresent() && event.getUser().get().isYourself()) {
            return;
        }
        for (DiscordInputItem item : getItems().get(event.getApi()).toArray(new DiscordInputItem[] {})) {
            if (!item.reactionAddEvent) {
                continue;
            }
            if (sharesChannelOrCategory(event.getChannel(), item.channel, item.category)
                    || sharesMessage(event.getMessage(), item.message)) {
                ChatDirector.run(item,
                        ((DiscordModule) ChatDirector.getConfig().getModule(DiscordModule.class)).getContext(event),
                        true);
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

    @Override
    public void addItem(IItem item) {
        DiscordInputItem discordItem = (DiscordInputItem) item;
        List<DiscordInputItem> tempItems;
        if(!pendingItems.containsKey(discordItem.bot)){
            tempItems=new ArrayList<>();
            pendingItems.put(discordItem.bot,tempItems);
        } else {
            tempItems=pendingItems.get(discordItem.bot);
        }
        tempItems.add(discordItem);
    }
}