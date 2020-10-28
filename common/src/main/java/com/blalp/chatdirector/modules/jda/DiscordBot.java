package com.blalp.chatdirector.modules.jda;

import javax.security.auth.login.LoginException;

import com.blalp.chatdirector.model.Loadable;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot extends Loadable {
    private JDA discordApi;
    private String token;
    public DiscordInputDaemon daemon;

    public DiscordBot(String token) {
        this.token = token;
    }

    /**
     * @return the discordApi
     */
    public JDA getDiscordApi() {
        return discordApi;
    }

    @Override
    public void load() {
        JDABuilder builder = JDABuilder.createDefault(token);
        try {
            discordApi = builder.build();
        } catch (LoginException e) {
            System.err.println("FAILED TO INITIALIZE BOT");
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {
        discordApi.shutdown();
    }
}