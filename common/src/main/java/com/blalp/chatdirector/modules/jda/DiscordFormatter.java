package com.blalp.chatdirector.modules.jda;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.modules.format.Formatter;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;



public class DiscordFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        Map<String,String> context = new HashMap<>();
        if(event instanceof MessageReceivedEvent) {
            context.put("DISCORD_MESSAGE", ((MessageReceivedEvent)event).getMessage().getContentRaw());
            context.put("DISCORD_AUTHOR_ID", ((MessageReceivedEvent)event).getMessage().getAuthor().getId());
            context.put("DISCORD_CHANNEL_ID", ((MessageReceivedEvent)event).getChannel().getId());
            context.put("DISCORD_AUTHOR_NAME", ((MessageReceivedEvent)event).getAuthor().getName());
            context.put("DISCORD_SELF_ID", ((MessageReceivedEvent)event).getJDA().getSelfUser().getId());
            if(((MessageReceivedEvent)event).getChannel() instanceof TextChannel){
                TextChannel channel = (TextChannel)((MessageReceivedEvent)event).getChannel();
                channel.getGuild().retrieveMember(((MessageReceivedEvent)event).getAuthor(),false).complete();
                if(((MessageReceivedEvent)event).getMember()!=null){
                    if(((MessageReceivedEvent)event).getMember().getNickname()!=null) {
                        context.put("DISCORD_AUTHOR_NICK_NAME", ((MessageReceivedEvent)event).getMember().getNickname());
                    } else {
                        context.put("DISCORD_AUTHOR_NICK_NAME", context.get("DISCORD_AUTHOR_NAME"));
                    }
                    context.put("DISCORD_AUTHOR_ROLE", ((MessageReceivedEvent)event).getMember().getRoles().get(0).getName());
                }
                context.put("DISCORD_AUTHOR_ROLE_COLOR", Integer.toString(((MessageReceivedEvent)event).getMember().getRoles().get(0).getColorRaw()));
                if(context.get("DISCORD_AUTHOR_ROLE").equals("@everyone")){
                    context.put("DISCORD_AUTHOR_ROLE", "Default");
                }
            } else {
                context.put("DISCORD_AUTHOR_ROLE", "DMs");
                context.put("DISCORD_AUTHOR_NICK_NAME",context.get("DISCORD_AUTHOR_NAME"));
            }
        }
        return context;
    }
    
}
