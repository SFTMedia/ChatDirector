package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.format.IFormatter;
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
        return new String[]{"discord-input","discord-output","discord-output-file","discord-output-reaction","discord-resolve","discord-embed","discord-get-dm-channel","discord-message-history","discord-create-channel","discord-delete-channel","discord-rename-channel"};
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
                DiscordInputItem item = new DiscordInputItem((String)configMap.get("bot"));
                if(configMap.containsKey("channel")) {
                    item.channelID=(String)configMap.get("channel");
                }
                if(configMap.containsKey("server")) {
                    item.serverID=(String)configMap.get("server");
                }
                if(configMap.containsKey("format")) {
                    item.format=(String)configMap.get("format");
                }
                if(configMap.containsKey("message-event")) {
                    item.message=(boolean)configMap.get("message-event");
                }
                if(configMap.containsKey("category")) {
                    item.categoryID=(String)configMap.get("category");
                }
                if(configMap.containsKey("message")) {
                    item.messageID=(String)configMap.get("message");
                }
                if(configMap.containsKey("reaction-add-event")) {
                    item.reactionAdd=(boolean)configMap.get("reaction-add-event");
                }
                if(configMap.containsKey("reaction-remove-event")) {
                    item.reactionRemove=(boolean)configMap.get("reaction-remove-event");
                }
                discordBots.get(configMap.get("bot")).daemon.addItem(item);
                return item;
            case "discord-output":
                DiscordOutputItem outputItem = new DiscordOutputItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("format")) {
                    outputItem.format=(String)configMap.get("format");
                }
                return outputItem;
            case "discord-output-file":
                DiscordOutputFileItem outputFileItem = new DiscordOutputFileItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("file-name")) {
                    outputFileItem.name=(String)configMap.get("file-name");
                }
                return outputFileItem;
            case "discord-file":
                DiscordOutputFileItem outFile = new DiscordOutputFileItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("name")){
                    outFile.name=(String)configMap.get("name");
                }
                return outFile;
            case "discord-output-reaction":
                return new DiscordOutputReactionItem((String)configMap.get("bot"),(String)configMap.get("channel"),(boolean)configMap.get("add"),(String)configMap.get("emoji"),(String)configMap.get("message"));
            case "discord-resolve":
                return new DiscordResolveItem((String)configMap.get("bot"),(String)configMap.get("server"),(boolean)configMap.get("to-discord"),(boolean)configMap.get("to-plain"));
            case "discord-embed":
                DiscordEmbedItem itemEmbed = new DiscordEmbedItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("author-avatar")){
                    itemEmbed.authorAvatar= (String) configMap.get("author-avatar");
                }
                if(configMap.containsKey("author-link")){
                    itemEmbed.authorLink= (String) configMap.get("author-link");
                }
                if(configMap.containsKey("author-name")){
                    itemEmbed.authorName= (String) configMap.get("author-name");
                }
                if(configMap.containsKey("color")){
                    itemEmbed.color= (String) configMap.get("color");
                }
                if(configMap.containsKey("description")){
                    itemEmbed.description= (String) configMap.get("description");
                }
                if(configMap.containsKey("footer-avatar")){
                    itemEmbed.footerAvatar= (String) configMap.get("footer-avatar");
                }
                if(configMap.containsKey("footer-name")){
                    itemEmbed.footerName = (String) configMap.get("footer-name");
                }
                if(configMap.containsKey("fields")){
                    itemEmbed.fields= (Map<String, String>) configMap.get("fields");
                }
                if(configMap.containsKey("image")){
                    itemEmbed.image= (String) configMap.get("image");
                }
                if(configMap.containsKey("inline-fields")){
                    itemEmbed.inlineFields= (Map<String, String>) configMap.get("inline-fields");
                }
                if(configMap.containsKey("thumbnail")){
                    itemEmbed.thumbnail= (String) configMap.get("thumbnail");
                }
                if(configMap.containsKey("title")){
                    itemEmbed.title= (String) configMap.get("title");
                }
                return itemEmbed;
            case "discord-get-dm-channel":
                return new DiscordGetDMChannelItem((String)configMap.get("bot"),(String)configMap.get("user"));
            case "discord-message-history":
                DiscordMessageHistoryItem historyItem = new DiscordMessageHistoryItem((String)configMap.get("bot"),(String)configMap.get("channel"));
                if(configMap.containsKey("length")){
                    historyItem.length= (int) configMap.get("length");
                }
                if(configMap.containsKey("format")){
                    historyItem.format= (String) configMap.get("format");
                }
                return historyItem;
            case "discord-create-channel":
                DiscordCreateChannelItem createChannelItem = new DiscordCreateChannelItem((String)configMap.get("bot"),(String)configMap.get("server"));
                if(configMap.containsKey("channel-name")){
                    createChannelItem.name=(String)configMap.get("channel-name");
                }
                if(configMap.containsKey("category")){
                    createChannelItem.categoryID=(String)configMap.get("category");
                }
                if(configMap.containsKey("user")){
                    createChannelItem.userID=(String)configMap.get("user");
                }
                if(configMap.containsKey("topic")){
                    createChannelItem.topic=(String)configMap.get("topic");
                }
                return createChannelItem;
            case "discord-delete-channel":
                return new DiscordDeleteChannel((String)configMap.get("bot"),(String)configMap.get("server"),(String)configMap.get("category"),(String)configMap.get("channel"));
            case "discord-rename-channel":
                return new DiscordChannelRename((String)configMap.get("bot"), (String)configMap.get("channel"), (String)configMap.get("name"));
            default:
                return null;
        }
    }
    public IFormatter getFormatter(){
        return formatter;
    }
    
}
