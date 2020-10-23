package com.blalp.chatdirector.modules.discord;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

import org.apache.commons.lang.NotImplementedException;


public class DiscordModule extends Module {
    public static Map<String, DiscordBot> discordBots = new HashMap<>();

    @Override
    public void load() {
        for(DiscordBot bot : discordBots.values()){
            bot.load();
        }
    }

    @Override
    public void unload() {
        for(DiscordBot bot : discordBots.values()){
            bot.unload();
        }
        discordBots = new HashMap<>();
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"DiscordInput","DiscordOutput","DiscordResolve"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "DiscordInput":
                try {
                    throw new NotImplementedException("BOTNAME NOT READ FROM Config DiscordModule createItem");
                } catch (NotImplementedException e){
                    e.printStackTrace();
                }
                if(discordBots.get("botname").daemon==null){
                    discordBots.get("botname").daemon=new DiscordInputDaemon("botname");
                }
                DiscordItem item = new DiscordItem();
                discordBots.get("botname").daemon.addItem(item);
                return item;
            case "DiscordOutput":
                return new DiscordOutputItem();
            case "DiscordResolve":
                return new DiscordResolveItem();
        }
        return null;
    }
    
}
