package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;

public class DiscordParticipatesItem extends DiscordItem {
    public int length = 50;
    Chain each;
    String channelID;

    public DiscordParticipatesItem(String botName, String channel,Chain each) {
        super(botName);
        this.channelID=channel;
        this.each=each;
    }
    @Override
    public Context process(Context context) {
        MessageSet messages = DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(ChatDirector.format(channelID, context)).get().getMessages(length).join();
        HashMap<String,User> users = new HashMap<>();
        for(Message message : messages) {
            if(message.getUserAuthor().isPresent()&&!message.getAuthor().isYourself()){
                users.put(message.getUserAuthor().get().getIdAsString(),message.getUserAuthor().get());
            }
        }
        Context workingContext = new Context();
        for(User user : users.values()){
            ChatDirector.logDebug("Starting work for "+user.getName()+" ("+user.getIdAsString()+")");
            workingContext.merge(DiscordModule.instance.getContext(user)); // All keys should be the same for each user and get overridden.
            each.run(workingContext);
            workingContext = new Context();
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return each!=null&&ValidationUtils.hasContent(channelID)&&super.isValid();
    }
}
