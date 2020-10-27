package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.model.Loadable;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot extends Loadable {
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
    public void load() {
        discordApi = new DiscordApiBuilder().setToken(token).login().join();
    }

    @Override
    public void unload() {
        discordApi.disconnect();
    }
}