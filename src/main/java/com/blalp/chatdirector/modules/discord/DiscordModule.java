package com.blalp.chatdirector.modules.discord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;


public class DiscordModule extends Module {
    public IFormatter formatter = new DiscordFormatter();
    public DiscordModule(LinkedHashMap<String,LinkedHashMap<String,String>> map){
        for (Entry<String,String> botMap : map.get("bots").entrySet()) {
            DiscordBot bot = new DiscordBot(botMap.getValue());
            discordBots.put(botMap.getKey(), bot);
        }
    }
    public static Map<String, DiscordBot> discordBots = new HashMap<>();

    @Override
    public void load() {
        ChatDirector.addFormatter(new DiscordFormatter());
        for(DiscordBot bot : discordBots.values()){
            bot.load();
            if(bot.daemon!=null){
                bot.daemon.load();
            }
        }
    }

    @Override
    public void unload() {
        for(DiscordBot bot : discordBots.values()){
            bot.unload();
            if(bot.daemon!=null){
                bot.daemon.unload();
            }
        }
        discordBots = new HashMap<>();
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"discord-input","discord-output","discord-resolve","discord-embed"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String, Object> configMap = (LinkedHashMap<String, Object>)config;
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
                    discordBots.get(configMap.get("bot")).daemon=new DiscordInputDaemon((String)configMap.get("bot"));
                }
                DiscordItem item = new DiscordItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("format")) {
                    item.format=(String)configMap.get("format");
                }
                discordBots.get(configMap.get("bot")).daemon.addItem(item);
                return item;
            case "discord-output":
                return new DiscordOutputItem((String)configMap.get("bot"),(String)configMap.get("channel"));
            case "discord-resolve":
                return new DiscordResolveItem((String)configMap.get("bot"),(String)configMap.get("server"),(boolean)configMap.get("to-discord"),(boolean)configMap.get("to-plain"));
            case "discord-embed":
                DiscordEmbedItem itemEmbed = new DiscordEmbedItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("format-author-avatar")){
                    itemEmbed.authorAvatar= (String) configMap.get("format-author-avatar");
                }
                if(configMap.containsKey("format-author-link")){
                    itemEmbed.authorLink= (String) configMap.get("format-author-link");
                }
                if(configMap.containsKey("format-author-name")){
                    itemEmbed.authorName= (String) configMap.get("format-author-name");
                }
                if(configMap.containsKey("color")){
                    itemEmbed.color= (String) configMap.get("color");
                }
                if(configMap.containsKey("format-description")){
                    itemEmbed.description= (String) configMap.get("format-description");
                }
                if(configMap.containsKey("format-footer-avatar")){
                    itemEmbed.footerAvatar= (String) configMap.get("format-footer-avatar");
                }
                if(configMap.containsKey("format-footer-name")){
                    itemEmbed.footerName = (String) configMap.get("format-footer-name");
                }
                if(configMap.containsKey("fields")){
                    itemEmbed.fields= (Map<String, String>) configMap.get("fields");
                }
                if(configMap.containsKey("format-image")){
                    itemEmbed.image= (String) configMap.get("format-image");
                }
                if(configMap.containsKey("inline-fields")){
                    itemEmbed.inlineFields= (Map<String, String>) configMap.get("inline-fields");
                }
                if(configMap.containsKey("format-thumbnail")){
                    itemEmbed.thumbnail= (String) configMap.get("format-thumbnail");
                }
                if(configMap.containsKey("format-title")){
                    itemEmbed.title= (String) configMap.get("format-title");
                }
                return itemEmbed;
        }
        return null;
    }
    public IFormatter getFormatter(){
        return formatter;
    }
    
}
