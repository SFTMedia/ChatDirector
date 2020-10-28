package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.model.format.Formatter;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        if(event instanceof MessageCreateEvent) {
            context.put("DISCORD_MESSAGE", ((MessageCreateEvent)event).getMessageContent());
            context.put("DISCORD_AUTHOR_ID", ((MessageCreateEvent)event).getMessage().getAuthor().getIdAsString());
            context.put("DISCORD_SELF_ID", ((MessageCreateEvent)event).getApi().getYourself().getIdAsString());
            context.put("DISCORD_CHANNEL_ID", ((MessageCreateEvent)event).getChannel().getIdAsString());
            context.put("DISCORD_AUTHOR_NAME", ((MessageCreateEvent)event).getMessageAuthor().getName());
            context.put("DISCORD_AUTHOR_DISPLAY_NAME", ((MessageCreateEvent)event).getMessageAuthor().getDisplayName());
            if(((MessageCreateEvent)event).getServer().isPresent()){
                if(((MessageCreateEvent)event).getMessageAuthor().asUser().get().getNickname(((MessageCreateEvent)event).getServer().get()).isPresent()){
                    context.put("DISCORD_AUTHOR_NICK_NAME", ((MessageCreateEvent)event).getMessageAuthor().asUser().get().getNickname(((MessageCreateEvent)event).getServer().get()).get());
                } else {
                    context.put("DISCORD_AUTHOR_NICK_NAME", context.get("DISCORD_AUTHOR_DISPLAY_NAME"));
                }
                List<Role> roles = ((MessageCreateEvent)event).getMessageAuthor().asUser().get().getRoles(((MessageCreateEvent)event).getServer().get());
                context.put("DISCORD_AUTHOR_ROLE", roles.get(roles.size()-1).getName());
                if(((MessageCreateEvent)event).getMessageAuthor().getRoleColor().isPresent()){
                    context.put("DISCORD_AUTHOR_ROLE_COLOR", Integer.toString(((MessageCreateEvent)event).getMessageAuthor().getRoleColor().get().getRGB()));
                }
                if(context.get("DISCORD_AUTHOR_ROLE").equals("@everyone")){
                    context.put("DISCORD_AUTHOR_ROLE", "Default");
                }
            } else {
                context.put("DISCORD_AUTHOR_ROLE", "DMs");
                context.put("DISCORD_AUTHOR_NICK_NAME",context.get("DISCORD_AUTHOR_DISPLAY_NAME"));
            }
        }
        return context;
    }
    
}
