package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class DiscordDeleteChannel extends DiscordItem {

    private String channelID;

    public DiscordDeleteChannel(String botName, String channel) {
        super(botName);
        this.channelID=channel;
    }
    @Override
    public Context process(Context context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getServerChannelById(ChatDirector.format(channelID, context)).get().delete("Deleted by Chatdirector");
        return new Context();
    }
    @Override
    public boolean isValid() {
        return super.isValid()&&ValidationUtils.hasContent(channelID);
    }
    
}
