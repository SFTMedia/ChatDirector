package com.blalp.chatdirector.modules.jda;

import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;



public class DiscordEmbedItem extends DiscordItem {
    public DiscordEmbedItem(String botName, String channelID) {
        super(botName, channelID);
    }
    public String title,description,authorName,authorLink,authorAvatar,color,footerName,footerAvatar,image,thumbnail;
    public Map<String,String> fields,inlineFields;

    @Override
    public String process(String string, Map<String, String> context) {
        EmbedBuilder embed = new EmbedBuilder();
        String temp;
        if(title!=null) {
            embed.setTitle(ChatDirector.formatter.format(title, context));
        }
        if(description!=null){
            embed.setDescription(ChatDirector.formatter.format(description,context));
        }
        if(authorName!=null){
            if(authorAvatar!=null){
                embed.setAuthor(ChatDirector.formatter.format(authorName,context),ChatDirector.formatter.format(authorLink,context),ChatDirector.formatter.format(authorAvatar,context));
            } else {
                embed.setAuthor(ChatDirector.formatter.format(authorName,context));
            }
        }
        if(color!=null){
            if(Color.decode(color)!=null){
                embed.setColor(Color.decode(color));
            } else {
                System.err.println("color "+color+" not a valid color.");
            }
        }
        if(footerName!=null){
            if(footerAvatar!=null){
                embed.setFooter(ChatDirector.formatter.format(footerName,context), ChatDirector.formatter.format(footerAvatar,context));
            } else {
                embed.setFooter(ChatDirector.formatter.format(footerName,context));
            }
        }
        if(image!=null){
            embed.setImage(ChatDirector.formatter.format(image,context));
        }
        if(thumbnail!=null){
            embed.setThumbnail(ChatDirector.formatter.format(thumbnail,context));
        }
        if(fields!=null) {
            for (Entry<String,String> field : fields.entrySet()){
                temp=ChatDirector.formatter.format(field.getValue(),context);
                if(!temp.isEmpty()) {
                    embed.addField(ChatDirector.formatter.format(field.getKey(),context), temp,false);
                }
            }
        }
        if(inlineFields!=null) {
            for (Entry<String,String> field : inlineFields.entrySet()){
                temp=ChatDirector.formatter.format(field.getValue(),context);
                if(!temp.isEmpty()) {
                    embed.addField(ChatDirector.formatter.format(field.getKey(),context), temp,true);
                }
            }
        }
        DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(channelID).sendMessage(embed.build());
        return string;
    }
    
}
