package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

/**
 * DiscordOutput
 */
public class DiscordOutputItem extends DiscordItem {
    String channelID;
    public DiscordOutputItem(String botName,String channelID) {
        super(botName);
    }
    @Override
    public Context process(Context context) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(ChatDirector.format(channelID,context)).get().asTextChannel().get().sendMessage(context.getCurrent());
        return new Context();
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(channelID)&&super.isValid();
    }
}