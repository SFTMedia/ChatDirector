package com.blalp.chatdirector.modules.javacord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.Event;
import org.javacord.api.event.channel.TextChannelEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.SingleReactionEvent;
import org.javacord.api.event.user.OptionalUserEvent;

public class DiscordModule implements IModule {

    @Override
    public boolean load() {
        boolean result = true;
        if (ChatDirector.getConfig().getModuleData() == null
                || !ChatDirector.getConfig().getModuleData().containsKey("discord")) {
            System.err.println("discord module does not have any data");
            return true;
        }
        for (Entry<String, String> bot : ChatDirector.getConfig().getModuleData().get("discord").entrySet()) {
            if (!DiscordBot.discordBots.containsKey(bot.getKey())) {
                DiscordBot.discordBots.put(bot.getKey(), new DiscordBot(bot.getValue()));
            }
        }
        for (Entry<String, DiscordBot> bot : DiscordBot.discordBots.entrySet()) {
            result = result && bot.getValue().load();
            if (DiscordBot.getPendingItems().containsKey(bot.getKey())) {
                for (DiscordInputItem item : DiscordBot.getPendingItems().get(bot.getKey())) {
                    if (bot.getValue().daemon == null) {
                        bot.getValue().daemon = new DiscordInputDaemon(bot.getKey());
                    }
                    bot.getValue().daemon.addItem(item);
                }
            }
            if (bot.getValue().daemon != null) {
                result = result && bot.getValue().daemon.load();
            }
        }
        return result;
    }

    @Override
    public boolean unload() {
        boolean result = true;
        for (DiscordBot bot : DiscordBot.discordBots.values()) {
            result = result && bot.unload();
            if (bot.daemon != null) {
                result = result && bot.daemon.unload();
            }
        }
        DiscordBot.discordBots = new HashMap<>();
        return result;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("discord-input", "discord-output", "discord-output-file", "discord-output-reaction",
                "discord-resolve", "discord-embed", "discord-get-dm-channel", "discord-message-history",
                "discord-create-channel", "discord-delete-channel", "discord-rename-channel", "discord-participants",
                "discord-delete-message");
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context getContext(Object event) {
        Context context = new Context();
        if (event instanceof Event) {
            context.put("DISCORD_SELF_ID", ((Event) event).getApi().getYourself().getIdAsString());
        }
        if (event instanceof User) {
            context.put("DISCORD_USER_ID", ((User) event).getIdAsString());
            context.put("DISCORD_USER_NAME", ((User) event).getName());
        }
        if (event instanceof OptionalUserEvent) {
            if (((OptionalUserEvent) event).getUser().isPresent()) {
                context.merge(getContext(((OptionalUserEvent) event).getUser().get()));
            }
        }
        if (event instanceof Message) {
            context.put("DISCORD_MESSAGE_CREATE_TIME", ((Message) event).getCreationTimestamp().toString());
            context.put("DISCORD_MESSAGE_EDIT_TIME", ((Message) event).getLastEditTimestamp().toString());
            context.put("DISCORD_AUTHOR_ID", ((Message) event).getAuthor().getIdAsString());
            context.put("DISCORD_USER_ID", ((Message) event).getAuthor().getIdAsString());
            context.put("DISCORD_MESSAGE", ((Message) event).getContent());
            context.put("DISCORD_MESSAGE_ID", ((Message) event).getIdAsString());
            context.put("DISCORD_AUTHOR_NAME", ((Message) event).getAuthor().getName());
            context.put("DISCORD_AUTHOR_DISPLAY_NAME", ((Message) event).getAuthor().getDisplayName());
            if (((Message) event).getAuthor().asUser().isPresent()) {
                context.merge(getContext(((Message) event).getAuthor().asUser().get()));
            }
            if ((((Message) event).getAuthor().asUser()).isPresent()) {
                context.merge(getContextUserServer(((Message) event).getAuthor().asUser().get(),
                        ((Message) event).getServer()));
            }
            if (((Message) event).getAuthor().getRoleColor().isPresent()) {
                context.put("DISCORD_AUTHOR_ROLE_COLOR",
                        Integer.toString(((Message) event).getAuthor().getRoleColor().get().getRGB()));
            }
        }
        if (event instanceof MessageCreateEvent) {
            context.merge(getContext(((MessageCreateEvent) event).getMessage()));

        }
        if (event instanceof TextChannelEvent) {
            context.put("DISCORD_CHANNEL_ID", ((TextChannelEvent) event).getChannel().getIdAsString());
            if (((TextChannelEvent) event).getChannel().asServerChannel().isPresent()) {
                context.put("DISCORD_CHANNEL_NAME",
                        ((TextChannelEvent) event).getChannel().asServerChannel().get().getName());
            }
            if (((TextChannelEvent) event).getChannel().asPrivateChannel().isPresent()) {
                context.put("DISCORD_DM_USER_ID", ((TextChannelEvent) event).getChannel().asPrivateChannel().get()
                        .getRecipient().get().getIdAsString());
            }
            if (((TextChannelEvent) event).getChannel().asCategorizable().isPresent()
                    && ((TextChannelEvent) event).getChannel().asCategorizable().get().getCategory().isPresent()) {
                context.put("DISCORD_CATEGORY_ID", ((TextChannelEvent) event).getChannel().asCategorizable().get()
                        .getCategory().get().getIdAsString());
            }
        }
        if (event instanceof SingleReactionEvent) {
            context.put("DISCORD_REACTION_EMOJI", ((SingleReactionEvent) event).getEmoji().getMentionTag());
            if (((SingleReactionEvent) event).getCount().isPresent()) {
                context.put("DISCORD_REACTION_COUNT", Integer.toString(((SingleReactionEvent) event).getCount().get()));
            }
            if (((SingleReactionEvent) event).getMessage().isPresent()) {
                context.merge(getContext(((SingleReactionEvent) event).getMessage().get()));
            } else {
                context.merge(getContext(((SingleReactionEvent) event).requestMessage().join()));
            }
            User user = null;
            if (((SingleReactionEvent) event).getUser().isPresent()) {
                user = (((SingleReactionEvent) event).getUser().get());
            } else {
                user = ((SingleReactionEvent) event).requestUser().join();
            }
            context.merge(getContext(user));
            context.merge(getContextUserServer(user, ((SingleReactionEvent) event).getServer()));
        }
        return context;
    }

    private static Context getContextUserServer(User user, Optional<Server> server) {
        Context context = new Context();
        if (server.isPresent()) {
            context.put("DISCORD_SERVER_NAME", server.get().getName());
            context.put("DISCORD_SERVER_ID", server.get().getIdAsString());
            if (user.getNickname(server.get()).isPresent()) {
                context.put("DISCORD_AUTHOR_NICK_NAME", user.getNickname(server.get()).get());
            } else {
                context.put("DISCORD_AUTHOR_NICK_NAME", user.getDisplayName(server.get()));
            }
            List<Role> roles = user.getRoles(server.get());
            context.put("DISCORD_AUTHOR_ROLE", roles.get(roles.size() - 1).getName());
            if (context.get("DISCORD_AUTHOR_ROLE").equals("@everyone")) {
                context.put("DISCORD_AUTHOR_ROLE", "Default");
            }
            context.put("DISCORD_USER_DISPLAY_NAME", user.getDisplayName(server.get()));
        } else {
            context.put("DISCORD_AUTHOR_ROLE", "DMs");
            context.put("DISCORD_AUTHOR_NICK_NAME", user.getName());
        }
        return context;
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "discord-input":
            return DiscordInputItem.class;
        case "discord-output":
            return DiscordOutputItem.class;
        case "discord-output-file":
            return DiscordOutputFileItem.class;
        case "discord-output-reaction":
            return DiscordOutputReactionItem.class;
        case "discord-resolve":
            return DiscordResolveItem.class;
        case "discord-embed":
            return DiscordEmbedItem.class;
        case "discord-get-dm-channel":
            return DiscordGetDMChannelItem.class;
        case "discord-message-history":
            return DiscordMessageHistoryItem.class;
        case "discord-create-channel":
            return DiscordCreateChannelItem.class;
        case "discord-delete-channel":
            return DiscordDeleteChannel.class;
        case "discord-delete-message":
            return DiscordDeleteMessage.class;
        case "discord-rename-channel":
            return DiscordChannelRename.class;
        case "discord-participants":
            return DiscordParticipatesItem.class;
        default:
            return null;
        }
    }

    public Map<String, DiscordBot> getDiscordBots() {
        return DiscordBot.discordBots;
    }
}
