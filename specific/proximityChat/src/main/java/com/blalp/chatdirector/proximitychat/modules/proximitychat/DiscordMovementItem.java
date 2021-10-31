package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.modules.discord.DiscordBot;
import com.blalp.chatdirector.modules.discord.DiscordBots;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.user.User;

public class DiscordMovementItem implements IItem {

    String bot, category;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(bot, category);
    }

    @Override
    public Context process(Context context) {
        Context output = new Context();
        DiscordApi discord = ((DiscordBot) ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class))
                .get(bot)).getDiscordApi();
        Context players = context.getContextAtPath("PROXIMITY_CHAT_GROUP_");

        if (discord.getChannelCategoryById(category).isPresent()) {
            // Each channel in this category must be a valid vc, with the first one being
            // the Lobby.
            ChannelCategory channelCategory = discord.getChannelCategoryById(category).get();
            for (String discordID : players.keySet()) {
                int channelIndex = Integer.parseInt(players.get(discordID));
                if (channelIndex >= channelCategory.getChannels().size()) {
                    // We need to create a category for this player
                } else {
                    if (channelCategory.getChannels().get(channelIndex).asServerVoiceChannel().isPresent()) {
                        User user = discord.getUserById(discordID).join();
                        // Make sure the user is in one of the category's VCs
                        if (user.getConnectedVoiceChannel(channelCategory.getServer()).isPresent()
                                && user.getConnectedVoiceChannel(channelCategory.getServer()).get().getCategory()
                                        .isPresent()
                                && user.getConnectedVoiceChannel(channelCategory.getServer()).get().getCategory().get()
                                        .getIdAsString().equals(category)) {
                            // Move the User to the right VC
                            user.move(channelCategory.getChannels().get(channelIndex).asServerVoiceChannel().get());
                        }
                    } else {
                        output.put("ERROR",
                                "Discord channel " + channelCategory.getChannels().get(channelIndex).getName() + "("
                                        + channelCategory.getChannels().get(channelIndex).getId() + ")" + "In category "
                                        + category + " Is not a voice channel.");
                        return output;
                    }
                }
            }
        } else {
            output.put("ERROR", "Discord category " + category + " Not found.");
            return output;
        }
        return output;
    }

}
