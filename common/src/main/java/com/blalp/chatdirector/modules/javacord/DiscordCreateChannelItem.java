package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;

public class DiscordCreateChannelItem extends DiscordItem {
    String userID;
    String name="channel";
    String topic;
    public DiscordCreateChannelItem(String botName, String serverID) {
        super(botName, serverID);
    }

    @Override
    public String process(String string, Map<String, String> context) {
        DiscordApi api = DiscordModule.discordBots.get(botName).getDiscordApi();
        ServerTextChannelBuilder builder = api.getServerById(serverID).get().createTextChannelBuilder();
        if(categoryID!=null){
            builder.setCategory(api.getChannelCategoryById(ChatDirector.format(categoryID, context)).get());
        }
        if(userID!=null) {
            builder.addPermissionOverwrite(api.getUserById(ChatDirector.format(userID, context)).join(), new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES,PermissionType.READ_MESSAGES).build());
        }
        builder.setName(ChatDirector.format(name, context));
        builder.setAuditLogReason("Made By ChatDirector");
        if(topic!=null){
            builder.setTopic(ChatDirector.format(topic, context));
        }
        context.put("DISCORD_CHANNEL_ID",builder.create().join().getIdAsString());
        return super.process(string, context);
    }
}
