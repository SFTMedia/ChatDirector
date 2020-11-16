package com.blalp.chatdirector.modules.javacord;

import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;

public class DiscordCreateChannelItem extends DiscordItem {
    String userID;
    String name="channel";
    String topic;
    String serverID;
    String categoryID;
    public DiscordCreateChannelItem(String botName, String serverID) {
        super(botName);
        this.serverID=serverID;
    }

    @Override
    public Context process(Context context) {
        DiscordApi api = DiscordModule.discordBots.get(botName).getDiscordApi();
        ServerTextChannelBuilder builder = api.getServerById(ChatDirector.format(serverID, context)).get().createTextChannelBuilder();
        if(categoryID!=null){
            ChannelCategory category = api.getChannelCategoryById(ChatDirector.format(categoryID, context)).get();
            builder.setCategory(category);
            for(Entry<Long,Permissions> permission : category.getOverwrittenRolePermissions().entrySet()){
                builder.addPermissionOverwrite(api.getRoleById(permission.getKey()).get(), permission.getValue());
            }
            for(Entry<Long,Permissions> permission : category.getOverwrittenUserPermissions().entrySet()){
                builder.addPermissionOverwrite(api.getUserById(permission.getKey()).join(), permission.getValue());
            }
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
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(categoryID,userID,topic,name,serverID)&&super.isValid();
    }
}
