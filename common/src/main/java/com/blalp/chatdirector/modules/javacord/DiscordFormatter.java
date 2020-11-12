package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.blalp.chatdirector.model.format.Formatter;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.Event;
import org.javacord.api.event.channel.TextChannelEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.SingleReactionEvent;
import org.javacord.api.event.user.OptionalUserEvent;

public class DiscordFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        if(event instanceof Event) {
            context.put("DISCORD_SELF_ID", ((Event)event).getApi().getYourself().getIdAsString());
        }
        if(event instanceof User) {
            context.put("DISCORD_USER_ID", ((User)event).getIdAsString());
            context.put("DISCORD_USER_NAME", ((User)event).getName());
        }
        if(event instanceof OptionalUserEvent) {
            if(((OptionalUserEvent)event).getUser().isPresent()) {
                context.putAll(getContext(((OptionalUserEvent)event).getUser().get()));
            }
        }
        if(event instanceof Message) {
            context.put("DISCORD_MESSAGE_CREATE_TIME", ((Message)event).getCreationTimestamp().toString());
            context.put("DISCORD_MESSAGE_EDIT_TIME", ((Message)event).getLastEditTimestamp().toString());
            context.put("DISCORD_AUTHOR_ID", ((Message)event).getAuthor().getIdAsString());
            context.put("DISCORD_USER_ID", ((Message)event).getAuthor().getIdAsString());
            context.put("DISCORD_MESSAGE", ((Message)event).getContent());
            context.put("DISCORD_MESSAGE_ID", ((Message)event).getIdAsString());
            context.put("DISCORD_AUTHOR_NAME", ((Message)event).getAuthor().getName());
            context.put("DISCORD_AUTHOR_DISPLAY_NAME", ((Message)event).getAuthor().getDisplayName());
            if(((Message)event).getAuthor().asUser().isPresent()){
                context.putAll(getContext(((Message)event).getAuthor().asUser().get()));
            }
            if((((Message)event).getAuthor().asUser()).isPresent()) {
                context.putAll(getContextUserServer(((Message)event).getAuthor().asUser().get(), ((Message)event).getServer()));
            }
            if(((Message)event).getAuthor().getRoleColor().isPresent()){
                context.put("DISCORD_AUTHOR_ROLE_COLOR", Integer.toString(((Message)event).getAuthor().getRoleColor().get().getRGB()));
            }
        }
        if(event instanceof MessageCreateEvent) {
            context.putAll(getContext(((MessageCreateEvent)event).getMessage()));
            
        }
        if(event instanceof TextChannelEvent) {
            context.put("DISCORD_CHANNEL_ID", ((TextChannelEvent)event).getChannel().getIdAsString());
            if(((TextChannelEvent)event).getChannel().asServerChannel().isPresent()) {
                context.put("DISCORD_CHANNEL_NAME", ((TextChannelEvent)event).getChannel().asServerChannel().get().getName());
            }
            if(((TextChannelEvent)event).getChannel().asPrivateChannel().isPresent()) {
                context.put("DISCORD_DM_USER_ID", ((TextChannelEvent)event).getChannel().asPrivateChannel().get().getRecipient().getIdAsString());
            }
            if(((TextChannelEvent)event).getChannel().asCategorizable().isPresent()&&((TextChannelEvent)event).getChannel().asCategorizable().get().getCategory().isPresent()) {
                context.put("DISCORD_CATEGORY_ID", ((TextChannelEvent)event).getChannel().asCategorizable().get().getCategory().get().getIdAsString());
            }
        } 
        if (event instanceof SingleReactionEvent) {
            context.put("DISCORD_REACTION_EMOJI", ((SingleReactionEvent)event).getEmoji().getMentionTag());
            if(((SingleReactionEvent)event).getCount().isPresent()){
                context.put("DISCORD_REACTION_COUNT", Integer.toString(((SingleReactionEvent)event).getCount().get()));
            }
            if(((SingleReactionEvent)event).getMessage().isPresent()){
                context.putAll(getContext(((SingleReactionEvent)event).getMessage().get()));
            } else {
                context.putAll(getContext(((SingleReactionEvent)event).requestMessage().join()));
            }
            User user = null;
            if(((SingleReactionEvent)event).getUser().isPresent()){
                user=(((SingleReactionEvent)event).getUser().get());
            } else {
                user=((SingleReactionEvent)event).requestUser().join();
            }
            context.putAll(getContext(user));
            context.putAll(getContextUserServer(user, ((SingleReactionEvent)event).getServer()));
        }
        return context;
    }
    private static Map<String,String> getContextUserServer(User user,Optional<Server> server) {
        Map<String,String> context = new HashMap<>();
        if(server.isPresent()){
            context.put("DISCORD_SERVER_NAME", server.get().getName());
            context.put("DISCORD_SERVER_ID", server.get().getIdAsString());
            if(user.getNickname(server.get()).isPresent()){
                context.put("DISCORD_AUTHOR_NICK_NAME", user.getNickname(server.get()).get());
            } else {
                context.put("DISCORD_AUTHOR_NICK_NAME", user.getDisplayName(server.get()));
            }
            List<Role> roles = user.getRoles(server.get());
            context.put("DISCORD_AUTHOR_ROLE", roles.get(roles.size()-1).getName());
            if(context.get("DISCORD_AUTHOR_ROLE").equals("@everyone")){
                context.put("DISCORD_AUTHOR_ROLE", "Default");
            }
            context.put("DISCORD_USER_DISPLAY_NAME", user.getDisplayName(server.get()));
        } else {
            context.put("DISCORD_AUTHOR_ROLE", "DMs");
            context.put("DISCORD_AUTHOR_NICK_NAME",user.getName());
        }
        return context;
    }
    
}
