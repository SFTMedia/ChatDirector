package com.blalp.chatdirector.modules.javacord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IItem;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;

public class DiscordParticipatesItem extends DiscordItem {
    public int length = 50;
    IItem each;

    public DiscordParticipatesItem(String botName, String channel,IItem each) {
        super(botName);
        this.channelID=channel;
        this.each=each;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        MessageSet messages = DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(ChatDirector.format(channelID, context)).get().getMessages(length).join();
        HashMap<String,User> users = new HashMap<>();
        for(Message message : messages) {
            if(message.getUserAuthor().isPresent()&&!message.getAuthor().isYourself()){
                users.put(message.getUserAuthor().get().getIdAsString(),message.getUserAuthor().get());
            }
        }
        Map<String,String> workingContext = (HashMap<String,String>)((HashMap<String,String>)context).clone();
        for(User user : users.values()){
            if(Configuration.debug){
                System.out.println("Starting work for "+user.getName()+" ("+user.getIdAsString()+")");
            }
            workingContext.putAll(ChatDirector.getContext(user)); // All keys should be the same for each user and get overridden.
            each.startWork(string, false, workingContext);
            workingContext = (HashMap<String,String>)((HashMap<String,String>)context).clone();
        }
        return string;
    }
}
