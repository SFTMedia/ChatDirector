package com.blalp.chatdirector.modules.javacord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.model.ILoadable;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot implements ILoadable {
    public static Map<String, DiscordBot> discordBots = new HashMap<>();
    private static Map<String,List<DiscordInputItem>> pendingItems = new HashMap<>();
    private DiscordApi discordApi;
    private String token;
    public DiscordInputDaemon daemon;

    public DiscordBot(String token) {
        this.token = token;
    }

    /**
     * @return the discordApi
     */
    public DiscordApi getDiscordApi() {
        return discordApi;
    }

    @Override
    public boolean load() {
        discordApi = new DiscordApiBuilder().setToken(token).login().join();
        return true;
    }

    @Override
    public boolean unload() {
        if (discordApi != null) {
            discordApi.disconnect();
        }
        return true;
    }

	public static DiscordBot get(String bot) {
        if(discordBots.containsKey(bot)){
		    return discordBots.get(bot);
        } else {
            return null;
        }
	}

	public static void addPendingItems(String bot, DiscordInputItem item) {
        if(!pendingItems.containsKey(bot)){
            pendingItems.put(bot,new ArrayList<>());
        }
        List<DiscordInputItem> items = pendingItems.get(bot);
        items.add(item);
        pendingItems.put(bot,items);
	}
    public static Map<String, List<DiscordInputItem>> getPendingItems() {
        return pendingItems;
    }
}