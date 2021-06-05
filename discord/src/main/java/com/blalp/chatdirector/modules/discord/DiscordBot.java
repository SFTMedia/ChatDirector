package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.core.model.ILoadable;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot implements ILoadable {
    private DiscordApi discordApi;
    private String token;

    public DiscordBot(String token) {
        this.token = token;
    }

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
}