package com.blalp.chatdirector.modules.javacord;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordEmbedItem extends DiscordItem {
    String channel;

    String title, description, authorName, authorLink, authorAvatar, color, footerName, footerAvatar, image, thumbnail;
    Map<String, String> fields, inlineFields;

    @Override
    public Context process(Context context) {
        EmbedBuilder embed = new EmbedBuilder();
        String temp;
        if (title != null) {
            embed.setTitle(ChatDirector.format(title, context));
        }
        if (description != null) {
            embed.setDescription(ChatDirector.format(description, context));
        }
        if (authorName != null) {
            if (authorAvatar != null) {
                embed.setAuthor(ChatDirector.format(authorName, context), ChatDirector.format(authorLink, context),
                        ChatDirector.format(authorAvatar, context));
            } else {
                embed.setAuthor(ChatDirector.format(authorName, context));
            }
        }
        if (color != null) {
            if (Color.decode(color) != null) {
                embed.setColor(Color.decode(color));
            } else {
                ChatDirector.getLogger().log(Level.WARNING, "color " + color + " not a valid color.");
            }
        }
        if (footerName != null) {
            if (footerAvatar != null) {
                embed.setFooter(ChatDirector.format(footerName, context), ChatDirector.format(footerAvatar, context));
            } else {
                embed.setFooter(ChatDirector.format(footerName, context));
            }
        }
        if (image != null) {
            embed.setImage(ChatDirector.format(image, context));
        }
        if (thumbnail != null) {
            embed.setThumbnail(ChatDirector.format(thumbnail, context));
        }
        if (fields != null) {
            for (Entry<String, String> field : fields.entrySet()) {
                temp = ChatDirector.format(field.getValue(), context);
                if (!temp.isEmpty()) {
                    embed.addField(ChatDirector.format(field.getKey(), context), temp);
                }
            }
        }
        if (inlineFields != null) {
            for (Entry<String, String> field : inlineFields.entrySet()) {
                temp = ChatDirector.format(field.getValue(), context);
                if (!temp.isEmpty()) {
                    embed.addInlineField(ChatDirector.format(field.getKey(), context), temp);
                }
            }
        }
        ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot).getDiscordApi()
                .getChannelById(ChatDirector.format(channel, context)).get().asServerTextChannel().get()
                .sendMessage(embed);
        return new Context();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && ValidationUtils.hasContent(channel);
    }

}
