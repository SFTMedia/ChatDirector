package com.blalp.chatdirector.modules.javacord;

import java.util.HashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ILoadable;

public class DiscordBots extends HashMap<String,DiscordBot> implements ILoadable {

    @Override
    public boolean load() {
        for (Entry<String, String> bot : ChatDirector.getConfig().getModuleData().get("discord").entrySet()) {
            if (!this.containsKey(bot.getKey())) {
                this.put(bot.getKey(), new DiscordBot(bot.getValue()));
            }
        }
        boolean result = true;
        for (DiscordBot discordBot : this.values()){
            result = result && discordBot.load();
        }
        return result;
    }

    @Override
    public boolean unload() {
        boolean result = true;
        for (DiscordBot discordBot : this.values()){
            result = result && discordBot.unload();
        }
        return result;
    }
    
}
