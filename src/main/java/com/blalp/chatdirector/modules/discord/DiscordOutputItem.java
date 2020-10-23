package com.blalp.chatdirector.modules.discord;

/**
 * DiscordOutput
 */
public class DiscordOutputItem extends DiscordItem {
    private String botName;

    @Override
    public String process(String string) {
        DiscordModule.discordBots.get(botName).getDiscordApi().getChannelById(this.getChannelID()).get().asServerTextChannel().get().sendMessage(string);
        return string;
    }
}