package com.blalp.chatdirector.modules.discord;

import java.util.HashMap;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.ILoadable;

public class DiscordBots extends HashMap<String, DiscordBot> implements ILoadable {

    boolean loaded = false;

    @Override
    public boolean load() {
        if (!loaded) {
            loaded = true;
            if (ChatDirector.getConfig().getModuleData().containsKey("discord")) {
                for (Entry<String, String> bot : ChatDirector.getConfig().getModuleData().get("discord").entrySet()) {
                    if (!this.containsKey(bot.getKey())) {
                        DiscordBot api = new DiscordBot(bot.getValue());

                        // Set message content if any of the chains has a messageHistoryItem or
                        // inputItem
                        api.messageContent = ChatDirector.getConfig().getChains().values().stream()
                                .filter(chain -> chain.getItems().stream()
                                        .filter(item -> (item instanceof DiscordMessageHistoryItem
                                                || item instanceof DiscordInputItem)
                                                && ((DiscordItem) item).bot.equals(bot.getKey()))
                                        .iterator().hasNext())
                                .iterator().hasNext();

                        this.put(bot.getKey(), api);
                    }
                }
                boolean result = true;
                for (DiscordBot discordBot : this.values()) {
                    result = result && discordBot.load();
                }
                return result;
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean unload() {
        loaded = false;
        boolean result = true;
        for (DiscordBot discordBot : this.values()) {
            result = result && discordBot.unload();
        }
        return result;
    }

}
