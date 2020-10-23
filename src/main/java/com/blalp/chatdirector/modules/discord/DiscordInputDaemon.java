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
        for (DiscordItem item : (DiscordItem[])items.toArray()) {
            if (event.getChannel().getIdAsString().equals(item.getChannelID())) {
                if (output.equals("")) {
                    String name;
                    if (!message.getAuthor().asUser().get()
                            .getNickname(event.getChannel().asServerChannel().get().getServer()).isPresent()) {
                        name = message.getAuthor().getName();
                    } else {
                        name = message.getAuthor().asUser().get()
                                .getNickname(event.getChannel().asServerChannel().get().getServer()).get();
                    }
                    String role = event.getChannel().asServerChannel().get().getServer()
                            .getHighestRole(message.getAuthor().asUser().get()).get().getName();
                    if (role.equals("@everyone")) {
                        role = "Default";
                    }
                    output = "[" + role + "] " + name + ": " + message.getContent();
                }
                item.startWork(output,false,ChatDirector.formatter.getContext(event));
            }
        }
    }
}