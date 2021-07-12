package com.blalp.chatdirector.modules.discord;

import java.util.Map.Entry;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordCreateChannelItem extends DiscordItem {
    String user;
    String name = "channel";
    String topic;
    String server;
    String category;

    @Override
    public Context process(Context context) {
        DiscordApi api = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                .getDiscordApi();
        ServerTextChannelBuilder builder = api.getServerById(ChatDirector.format(server, context)).get()
                .createTextChannelBuilder();
        if (category != null) {
            ChannelCategory categoryObj = api.getChannelCategoryById(ChatDirector.format(category, context)).get();
            builder.setCategory(categoryObj);
            for (Entry<Long, Permissions> permission : categoryObj.getOverwrittenRolePermissions().entrySet()) {
                builder.addPermissionOverwrite(api.getRoleById(permission.getKey()).get(), permission.getValue());
            }
            for (Entry<Long, Permissions> permission : categoryObj.getOverwrittenUserPermissions().entrySet()) {
                builder.addPermissionOverwrite(api.getUserById(permission.getKey()).join(), permission.getValue());
            }
        }
        if (user != null) {
            builder.addPermissionOverwrite(api.getUserById(ChatDirector.format(user, context)).join(),
                    new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES, PermissionType.READ_MESSAGES)
                            .build());
        }
        builder.setName(ChatDirector.format(name, context));
        builder.setAuditLogReason("Made By ChatDirector");
        if (topic != null) {
            builder.setTopic(ChatDirector.format(topic, context));
        }
        context.put("DISCORD_CHANNEL_ID", builder.create().join().getIdAsString());
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(category, user, topic, name, server) && super.isValid();
    }
}
