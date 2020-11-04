package com.blalp.chatdirector.modules.javacord;

import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

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
            embed.setTitle(ChatDirector.format(title, context));
        }
        if(description!=null){
            embed.setDescription(ChatDirector.format(description,context));
        }
        if(authorName!=null){
            if(authorAvatar!=null){
                embed.setAuthor(ChatDirector.format(authorName,context),ChatDirector.format(authorLink,context),ChatDirector.format(authorAvatar,context));
            } else {
                embed.setAuthor(ChatDirector.format(authorName,context));
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
                embed.setFooter(ChatDirector.format(footerName,context), ChatDirector.format(footerAvatar,context));
            } else {
                embed.setFooter(ChatDirector.format(footerName,context));
            }
        }
        if(image!=null){
            embed.setImage(ChatDirector.format(image,context));
        }
        if(thumbnail!=null){
            embed.setThumbnail(ChatDirector.format(thumbnail,context));
        }
        if(fields!=null) {
            for (Entry<String,String> field : fields.entrySet()){
                temp=ChatDirector.format(field.getValue(),context);
                if(!temp.isEmpty()) {
                    embed.addField(ChatDirector.format(field.getKey(),context), temp);
                }
            }
        }
        if(inlineFields!=null) {
            for (Entry<String,String> field : inlineFields.entrySet()){
                temp=ChatDirector.format(field.getValue(),context);
                if(!temp.isEmpty()) {
                    embed.addInlineField(ChatDirector.format(field.getKey(),context), temp);
                }
            }
        }
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(channelID).get().asServerTextChannel().get().sendMessage(embed);
        return string;
    }
    
}
