package com.blalp.chatdirector.modules.discord;

import com.blalp.chatdirector.core.model.ILoadable;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

public class DiscordBot implements ILoadable {
    private DiscordApi discordApi;
    private String token;
    protected Boolean messageContent = false;

    public DiscordBot(String token) {
        this.token = token;
    }

    public DiscordApi getDiscordApi() {
        return discordApi;
    }

    @Override
    public boolean load() {
        DiscordApiBuilder builder = new DiscordApiBuilder().setToken(token);
        if (messageContent) {
            builder = builder.addIntents(Intent.MESSAGE_CONTENT);
        }
        discordApi = builder.login().join();
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