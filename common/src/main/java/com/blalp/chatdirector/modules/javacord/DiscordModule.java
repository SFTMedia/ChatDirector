package com.blalp.chatdirector.modules.javacord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    static DiscordModule instance;
    public DiscordModule(LinkedHashMap<String, LinkedHashMap<String, String>> map) {
        instance=this;
        for (Entry<String, String> botMap : map.get("bots").entrySet()) {
            DiscordBot bot = new DiscordBot(botMap.getValue());
            discordBots.put(botMap.getKey(), bot);
        }
    }

    public static Map<String, DiscordBot> discordBots = new HashMap<>();

    @Override
    public void load() {
        for (DiscordBot bot : discordBots.values()) {
            bot.load();
            if (bot.daemon != null) {
                bot.daemon.load();
            }
        }
    }

    @Override
    public void unload() {
        for (DiscordBot bot : discordBots.values()) {
            bot.unload();
            if (bot.daemon != null) {
                bot.daemon.unload();
            }
        }
        discordBots = new HashMap<>();
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("discord-input", "discord-output", "discord-output-file", "discord-output-reaction",
                "discord-resolve", "discord-embed", "discord-get-dm-channel", "discord-message-history",
                "discord-create-channel", "discord-delete-channel", "discord-rename-channel", "discord-participants");
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "discord-input":
                if (!discordBots.containsKey(config.get("bot").asText())) {
                    ChatDirector.log(Level.WARNING, "Please use an existing bot as specified in the module section, not "+ config.get("bot").asText());
                    return null;
                }
                if (discordBots.get(config.get("bot").asText()).daemon == null) {
                    discordBots.get(config.get("bot").asText()).daemon = new DiscordInputDaemon(config.get("bot").asText());
                }
                DiscordInputItem item = om.convertValue(config, DiscordInputItem.class);
                discordBots.get(config.get("bot").asText()).daemon.addItem(item,chain);
                return item;
            case "discord-output":
                return om.convertValue(config, DiscordOutputItem.class);
            case "discord-output-file":
                return om.convertValue(config, DiscordOutputFileItem.class);
            case "discord-output-reaction":
                return om.convertValue(config, DiscordOutputReactionItem.class);
            case "discord-resolve":
                return om.convertValue(config, DiscordResolveItem.class);
            case "discord-embed":
                return om.convertValue(config, DiscordEmbedItem.class);
            case "discord-get-dm-channel":
                return om.convertValue(config, DiscordGetDMChannelItem.class);
            case "discord-message-history":
                return om.convertValue(config, DiscordMessageHistoryItem.class);
            case "discord-create-channel":
                return om.convertValue(config, DiscordCreateChannelItem.class);
            case "discord-delete-channel":
                return om.convertValue(config, DiscordDeleteChannel.class);
            case "discord-rename-channel":
                return om.convertValue(config, DiscordChannelRename.class);
            case "discord-participants":
                DiscordParticipatesItem participatesItem = new DiscordParticipatesItem(config.get("bot").asText(),
                        config.get("channel").asText(),
                        ChatDirector.loadChain(om, config.get("each")));
                if (config.has("length")) {
                    participatesItem.length = config.get("length").asInt();
                }
                return participatesItem;
            default:
                return null;
        }
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
                        .getRecipient().getIdAsString());
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
}
