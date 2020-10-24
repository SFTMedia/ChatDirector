package com.blalp.chatdirector.modules.discord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;
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
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(channelID).get().asServerTextChannel().get().sendMessage(embed);
        return string;
    }
    
}
