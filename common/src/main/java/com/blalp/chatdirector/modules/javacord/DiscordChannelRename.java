package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class DiscordChannelRename extends DiscordItem {
    String name,channelID;
    public DiscordChannelRename(String botName, String channelID,String name) {
        super(botName);
        this.channelID=channelID;
        this.name=name;
    }
    @Override
    public Context process(Context context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getServerChannelById(ChatDirector.format(channelID, context)).get().updateName(ChatDirector.format(name, context));
        return new Context();
    }

    @Override
    public boolean isValid() {
        return super.isValid()&&ValidationUtils.hasContent(channelID,name);
    }
}
