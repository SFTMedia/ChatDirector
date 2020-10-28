package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.modules.format.Formatter;

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
            if(((MessageCreateEvent)event).getChannel().asServerChannel().isPresent()){
                if(((MessageCreateEvent)event).getMessageAuthor().asUser().get().getNickname(((MessageCreateEvent)event).getChannel().asServerChannel().get().getServer()).isPresent()){
                    context.put("DISCORD_AUTHOR_NICK_NAME", ((MessageCreateEvent)event).getMessageAuthor().asUser().get().getNickname(((MessageCreateEvent)event).getChannel().asServerChannel().get().getServer()).get());
                } else {
                    context.put("DISCORD_AUTHOR_NICK_NAME", context.get("DISCORD_AUTHOR_DISPLAY_NAME"));
                }
                context.put("DISCORD_AUTHOR_ROLE", ((MessageCreateEvent)event).getChannel().asServerChannel().get().getServer().getRoles(((MessageCreateEvent)event).getMessage().getUserAuthor().get()).get(0).getName());
                if(((MessageCreateEvent)event).getChannel().asServerChannel().get().getServer().getHighestRole(((MessageCreateEvent)event).getMessage().getUserAuthor().get()).get().getColor().isPresent()){
                    context.put("DISCORD_AUTHOR_ROLE_COLOR", Integer.toString(((MessageCreateEvent)event).getChannel().asServerChannel().get().getServer().getHighestRole(((MessageCreateEvent)event).getMessage().getUserAuthor().get()).get().getColor().get().getRGB()));
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
