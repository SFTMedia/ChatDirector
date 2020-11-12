package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;

public class DiscordDeleteChannel extends DiscordItem {

    public DiscordDeleteChannel(String botName,String server,String category,String channel) {
        super(botName);
        this.serverID=server;
        this.categoryID=category;
        this.channelID=channel;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        DiscordApi api = DiscordModule.discordBots.get(botName).getDiscordApi();
        ServerChannel channel = api.getServerChannelById(ChatDirector.format(channelID, context)).get();
        if(channel.asCategorizable().isPresent()){
            if(channel.asCategorizable().get().getCategory().isPresent()){
                if(!channel.asCategorizable().get().getCategory().get().getIdAsString().equals(categoryID)){
                    if(Configuration.debug){
                        System.out.println(channel.getIdAsString()+" was not in category "+categoryID);
                    }
                    return string;
                }
            }
        }
        if(!channel.getServer().getIdAsString().equals(serverID)){
            if(Configuration.debug){
                System.out.println(channel.getIdAsString()+" was not in category "+categoryID);
            }
            return string;
        }
        channel.delete("Deleted by ChatDirector");
        return super.process(string, context);
    }
    
}
