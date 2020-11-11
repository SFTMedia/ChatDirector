package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

/**
 * DiscordOutput
 */
public class DiscordOutputReactionItem extends DiscordItem {
    private String emoji;
    private boolean add;

    public DiscordOutputReactionItem(String botName, String channelID, boolean add, String emoji,String message) {
        super(botName,channelID);
        this.add=add;
        this.emoji=emoji;
    }
    @Override
    public String process(String string, Map<String,String> context) {
        DiscordApi api = DiscordModule.discordBots.get(botName).getDiscordApi();
        Message message = api.getMessageById(ChatDirector.format(messageID, context), api.getChannelById(ChatDirector.format(channelID, context)).get().asTextChannel().get()).join();
        if(add){ 
            if(api.getCustomEmojiById(ChatDirector.format(emoji,context)).isPresent()){
                message.addReaction(api.getCustomEmojiById(ChatDirector.format(emoji,context)).get());
            } else {
                message.addReaction(ChatDirector.format(emoji,context));
            }
        } else {
            if(api.getCustomEmojiById(ChatDirector.format(emoji,context)).isPresent()){
                message.removeReactionByEmoji(api.getCustomEmojiById(ChatDirector.format(emoji,context)).get());
            } else {
                message.removeReactionByEmoji(ChatDirector.format(emoji,context));
            }
        }
        return string;
    }
}