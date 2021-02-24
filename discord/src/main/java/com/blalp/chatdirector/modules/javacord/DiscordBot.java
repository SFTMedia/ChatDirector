package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.model.ILoadable;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot implements ILoadable {
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
}