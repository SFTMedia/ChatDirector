package com.blalp.chatdirector.modules.discord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

import org.apache.commons.lang.NotImplementedException;


public class DiscordModule extends Module {
    public DiscordModule(LinkedHashMap<String,ArrayList<LinkedHashMap<String,String>>> map){
        for (LinkedHashMap<String,String> botMap : map.get("bots")) {
            String botName = (String)botMap.keySet().toArray()[0];
            String token = (String)botMap.values().toArray()[0];
            DiscordBot bot = new DiscordBot(token);
            discordBots.put(botName, bot);
        }
    }
    public static Map<String, DiscordBot> discordBots = new HashMap<>();

    @Override
    public void load() {
        for(DiscordBot bot : discordBots.values()){
            bot.load();
        }
    }

    @Override
    public void unload() {
        for(DiscordBot bot : discordBots.values()){
            bot.unload();
        }
        discordBots = new HashMap<>();
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"discord-input","discord-output","discord-resolve"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String, String> configMap = (LinkedHashMap<String, String>)config;
        switch (type) {
            case "discord-input":
                if (!discordBots.containsKey(configMap.get("bot"))) {
                    try{
                        throw new NullPointerException("Please use an existing bot as specified in the module section, not "+configMap.get("bot"));
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                if(discordBots.get(configMap.get("bot")).daemon==null){
                    discordBots.get(configMap.get("bot")).daemon=new DiscordInputDaemon(configMap.get("bot"));
                }
                DiscordItem item = new DiscordItem(configMap.get("bot"),configMap.get("channel"));
                discordBots.get(configMap.get("bot")).daemon.addItem(item);
                return item;
            case "discord-output":
                return new DiscordOutputItem(configMap.get("bot"),configMap.get("channel"));
            case "discord-resolve":
                return new DiscordResolveItem(configMap.get("bot"),configMap.get("server"));
        }
        return null;
    }
    
}
