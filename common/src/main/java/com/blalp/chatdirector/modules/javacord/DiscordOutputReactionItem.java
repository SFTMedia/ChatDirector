package com.blalp.chatdirector.modules.javacord;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.user.User;

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
        this.messageID=message;
    }
    @Override
    public String process(String string, Map<String,String> context) {
        context.put("CURRENT", string);
        DiscordApi api = DiscordModule.discordBots.get(botName).getDiscordApi();
        Message message = api.getMessageById(ChatDirector.format(messageID, context), api.getChannelById(ChatDirector.format(channelID, context)).get().asTextChannel().get()).join();
        if(add){ 
            if(api.getCustomEmojiById(ChatDirector.format(emoji,context)).isPresent()){
                message.addReaction(api.getCustomEmojiById(ChatDirector.format(emoji,context)).get());
            } else {
                message.addReaction(ChatDirector.format(emoji,context));
            }
        } else {
            Reaction reaction;
            if(api.getCustomEmojiById(ChatDirector.format(emoji,context)).isPresent()){
                reaction = message.getReactionByEmoji(api.getCustomEmojiById(ChatDirector.format(emoji,context)).get()).get();
            } else {
                reaction= message.getReactionByEmoji(ChatDirector.format(emoji,context)).get();
            }
            for(User user : reaction.getUsers().join()){
                if(!user.isYourself()) {
                    reaction.removeUser(user);
                }
            }
        }
        return string;
    }
}