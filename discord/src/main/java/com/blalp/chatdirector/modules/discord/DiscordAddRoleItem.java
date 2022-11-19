package com.blalp.chatdirector.modules.discord;

import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordAddRoleItem extends DiscordItem {
    String user;
    String server;
    String role;

    @Override
    public Context process(Context context) {
        try {
            DiscordApi api = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
                    .getDiscordApi();
            Server server = api.getServerById(ChatDirector.format(this.server, context)).get();
            User user = api.getUserById(ChatDirector.format(this.user, context)).get();
            Role role = server.getRoleById(ChatDirector.format(this.role, context)).get();

            server.addRoleToUser(user, role).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(user, role, server) && super.isValid();
    }

}
