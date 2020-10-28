package com.blalp.chatdirector.modules.bungee;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePlayerlistItem extends Item {
    public String format = "```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n";
    public String formatNoPlayers = "**No online players**";
    public String formatPlayer = "%PLAYER_NAME%";
    public String formatServer = "%SERVER_NAME%";
    public boolean splitByServer = false;
    @Override
    public String process(String string, Map<String,String> context) {
        context.putAll(ChatDirector.formatter.getContext(ProxyServer.getInstance()));
        context.put("CURRENT", string);

        // Put it into pipe no matter what.

        String output = ChatDirector.formatter.format(format,context);
        String temp_output="";
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
                        temp_output += ", ";
                    } else {
                        first = false;
                    }
                    tempContext2.putAll(tempContext);
                    tempContext2.putAll(ChatDirector.formatter.getContext(player));
                    temp_output += ChatDirector.formatter.format(formatPlayer, tempContext2);
                    tempContext2= new HashMap<>();
                    tempContext2.putAll(tempContext);
                }
                if(temp_output.isEmpty()){
                    temp_output=ChatDirector.formatter.format(formatNoPlayers,context);
                }
                context.put("SERVER_"+server.getName()+"_PLAYERS",temp_output);
                context.put("SERVER_"+server.getName()+"_NUM_PLAYERS",Integer.toString(server.getPlayers().size()));
                output+=temp_output;
                temp_output="";
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