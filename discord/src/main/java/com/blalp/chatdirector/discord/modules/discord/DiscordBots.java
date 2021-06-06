package com.blalp.chatdirector.discord.modules.discord;

import java.util.HashMap;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.ILoadable;

public class DiscordBots extends HashMap<String, DiscordBot> implements ILoadable {

    boolean loaded=false;

    @Override
    public boolean load() {
        if(!loaded){
            loaded=true;
            System.out.println(ChatDirector.getConfig());
            if(ChatDirector.getConfig().getModuleData().containsKey("discord")) {
                for (Entry<String, String> bot : ChatDirector.getConfig().getModuleData().get("discord").entrySet()) {
                    if (!this.containsKey(bot.getKey())) {
                        this.put(bot.getKey(), new DiscordBot(bot.getValue()));
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
        loaded=false;
        boolean result = true;
        for (DiscordBot discordBot : this.values()) {
            result = result && discordBot.unload();
        }
        return result;
    }

}
