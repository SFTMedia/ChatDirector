package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class DiscordInputDaemon extends ItemDaemon implements MessageCreateListener {
    private String botName;

    public DiscordInputDaemon(String botName) {
        this.botName = botName;
    }

    @Override
    public void load() {
        DiscordModule.discordBots.get(botName).getDiscordApi().addMessageCreateListener(this);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().getId() == DiscordModule.discordBots.get(botName).getDiscordApi().getYourself().getId()) {
            return;
        }
        String output = "";
        for (DiscordItem item : items.toArray(new DiscordItem[]{})) {
            if (event.getChannel().getIdAsString().equals(item.getChannelID())) {
                if (output.equals("")) {
                    output=ChatDirector.formatter.format(item.format, ChatDirector.formatter.getContext(event));
                }
                item.startWork(output,false,ChatDirector.formatter.getContext(event));
            }
        }
    }
}