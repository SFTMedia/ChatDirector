package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.entity.user.User;

public class DiscordGetDMChannelItem extends DiscordItem {

    String userID;
    public DiscordGetDMChannelItem(String botName,String user) {
        super(botName);
        this.userID=user;
    }
    @Override
    public Context process(Context context) {
        User user = DiscordModule.discordBots.get(botName).getDiscordApi().getUserById(ChatDirector.format(userID, context)).join();
        if(!user.getPrivateChannel().isPresent()){
            user.openPrivateChannel().join();
        }
        Context output = new Context();
        output.put("DISCORD_DM_CHANNEL_ID", user.getPrivateChannel().get().getIdAsString());
		return output;
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(userID)&&super.isValid();
    }
}
