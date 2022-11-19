package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BungeePlayerlistItem implements IItem {
    String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    String formatNoPlayers = "**No online players**";
    String formatPlayer = "%PLAYER_NAME%";
    String formatServer = "%SERVER_NAME%";
    boolean splitByServer = false;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(format, formatNoPlayers, formatPlayer, formatServer);
    }

    @Override
    public Context process(Context context) {
        String output = ChatDirector.format(format, context);
        String temp_output = "";
        boolean first = true;
        Context tempContext = new Context(context);
        Context tempContext2 = new Context(context);
        if (splitByServer) {
            for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
                tempContext.merge(ChatDirector.getConfig().getModule(BungeeModule.class).getContext(server));
                output += ChatDirector.format(formatServer, tempContext);
                first = true;
                for (ProxiedPlayer player : server.getPlayers()) {
                    if (!first) {
                        temp_output += ", ";
                    } else {
                        first = false;
                    }
                    tempContext2.merge(tempContext);
                    tempContext2.merge(ChatDirector.getConfig().getModule(BungeeModule.class).getContext(player));
                    temp_output += ChatDirector.format(formatPlayer, tempContext2);
                    tempContext2 = new Context(tempContext);
                }
                if (temp_output.isEmpty()) {
                    temp_output = ChatDirector.format(formatNoPlayers, context);
                }
                context.put("SERVER_" + server.getName() + "_PLAYERS", temp_output);
                context.put("SERVER_" + server.getName() + "_NUM_PLAYERS",
                        Integer.toString(server.getPlayers().size()));
                output += temp_output;
                temp_output = "";
                tempContext = new Context(context);
            }
        } else {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (!first) {
                    output += ", ";
                } else {
                    first = false;
                }
                tempContext.merge(ChatDirector.getConfig().getModule(BungeeModule.class).getContext(player));
                output += ChatDirector.format(formatPlayer, tempContext);
                tempContext = new Context(context);
            }
        }
        output += "\n```";
        // Get global bungee stats
        context.merge(ChatDirector.getConfig().getModule(BungeeModule.class).getContext(null));
        if (output.equals(ChatDirector.format(format.replace("%NUM_PLAYERS%", "0"), context))) {
            output = ChatDirector.format(formatNoPlayers, context);
        }
        return new Context(output);
    }

}