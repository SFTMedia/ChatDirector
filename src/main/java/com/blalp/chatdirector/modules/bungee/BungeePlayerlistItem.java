package com.blalp.chatdirector.modules.bungee;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePlayerlistItem extends Item {
    public String triggerWord = "playerlist";
    public boolean ignoreCase = true;
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    public String formatPlayer = "%PLAYER_NAME%";
    public String formatServer = "%SERVER_NAME%";
    public boolean splitByServer = false;
    @Override
    public String process(String string, Map<String,String> context) {
        context.putAll(ChatDirector.formatter.getContext(ProxyServer.getInstance()));
        if (!((string.equalsIgnoreCase(triggerWord)&&ignoreCase)||(string.equals(triggerWord)))) {
            return string;
        }
        String output = ChatDirector.formatter.format(format,context);
        boolean first = true;
        Map<String,String> tempContext = new HashMap<>();
        Map<String,String> tempContext2 = new HashMap<>();
        tempContext.putAll(context);
        if(splitByServer){
            for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
                tempContext.putAll(ChatDirector.formatter.getContext(server));
                output += ChatDirector.formatter.format(formatServer, tempContext);
                first=true;
                for (ProxiedPlayer player : server.getPlayers()) {
                    if (!first) {
                        output += ", ";
                    } else {
                        first = false;
                    }
                    tempContext2.putAll(tempContext);
                    tempContext2.putAll(ChatDirector.formatter.getContext(player));
                    output += ChatDirector.formatter.format(formatPlayer, tempContext2);
                    tempContext2= new HashMap<>();
                    tempContext2.putAll(tempContext);
                }
                tempContext= new HashMap<>();
                tempContext.putAll(context);
            }
        } else {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (!first) {
                    output += ", ";
                } else {
                    first = false;
                }
                tempContext.putAll(ChatDirector.formatter.getContext(player));
                output += ChatDirector.formatter.format(formatPlayer, tempContext);
                tempContext= new HashMap<>();
                tempContext.putAll(context);
            }
        }
        output += "\n```";
        if (output.equals(ChatDirector.formatter.format(format.replace("%NUM_PLAYERS%", "0"),context))) {
            output = ChatDirector.formatter.format(formatNoPlayers,context);
        }
        return output;
    }
}