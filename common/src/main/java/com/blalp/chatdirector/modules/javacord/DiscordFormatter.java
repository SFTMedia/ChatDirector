package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.format.Formatter;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.Event;
import org.javacord.api.event.channel.TextChannelEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.SingleReactionEvent;

public class DiscordFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        if(event instanceof Event) {
            context.put("DISCORD_SELF_ID", ((Event)event).getApi().getYourself().getIdAsString());
        }
        if(event instanceof Message) {
            context.put("DISCORD_AUTHOR_ID", ((Message)event).getAuthor().getIdAsString());
            context.put("DISCORD_MESSAGE", ((Message)event).getContent());
            context.put("DISCORD_AUTHOR_NAME", ((Message)event).getAuthor().getName());
            context.put("DISCORD_AUTHOR_DISPLAY_NAME", ((Message)event).getAuthor().getDisplayName());
            if(((Message)event).getServer().isPresent()){
                if(((Message)event).getAuthor().asUser().get().getNickname(((Message)event).getServer().get()).isPresent()){
                    context.put("DISCORD_AUTHOR_NICK_NAME", ((Message)event).getAuthor().asUser().get().getNickname(((Message)event).getServer().get()).get());
                } else {
                    context.put("DISCORD_AUTHOR_NICK_NAME", context.get("DISCORD_AUTHOR_DISPLAY_NAME"));
                }
                List<Role> roles = ((Message)event).getAuthor().asUser().get().getRoles(((Message)event).getServer().get());
                context.put("DISCORD_AUTHOR_ROLE", roles.get(roles.size()-1).getName());
                if(((Message)event).getAuthor().getRoleColor().isPresent()){
                    context.put("DISCORD_AUTHOR_ROLE_COLOR", Integer.toString(((Message)event).getAuthor().getRoleColor().get().getRGB()));
                }
                if(context.get("DISCORD_AUTHOR_ROLE").equals("@everyone")){
                    context.put("DISCORD_AUTHOR_ROLE", "Default");
                }
            } else {
                context.put("DISCORD_AUTHOR_ROLE", "DMs");
                context.put("DISCORD_AUTHOR_NICK_NAME",context.get("DISCORD_AUTHOR_DISPLAY_NAME"));
            }
        }
        if(event instanceof MessageCreateEvent) {
            context.putAll(ChatDirector.getContext(((MessageCreateEvent)event).getMessage()));
        }
        if(event instanceof TextChannelEvent) {
            context.put("DISCORD_CHANNEL_ID", ((TextChannelEvent)event).getChannel().getIdAsString());
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
                context.putAll(ChatDirector.getContext(((SingleReactionEvent)event).getMessage().get()));
            }
        }
        return context;
    }
    
}
