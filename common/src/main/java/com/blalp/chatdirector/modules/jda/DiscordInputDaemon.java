package com.blalp.chatdirector.modules.jda;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.model.ItemDaemon;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class DiscordInputDaemon extends ListenerAdapter implements ILoadable {
    private String botName;
    public ArrayList<DiscordItem> items = new ArrayList<>();

    public DiscordInputDaemon(String botName) {
        this.botName = botName;
    }

    @Override
    public void load() {
        DiscordModule.discordBots.get(botName).getDiscordApi().addEventListener(this);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        for (DiscordItem item : items) {
            if (event.getChannel().getId().equals(item.getChannelID())) {
                item.startWork(ChatDirector.format(item.format, ChatDirector.formatter.getContext(event)),false,ChatDirector.formatter.getContext(event));
            }
        }
    }

    @Override
    public void unload() {
    }

    @Override
    public void reload() {
        unload();
        load();
    }

	public void addItem(DiscordItem item) {
        items.add(item);
	}
}