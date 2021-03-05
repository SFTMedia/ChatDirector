package com.blalp.chatdirector.modules.javacord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestDiscord {

    static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/javacord/config.yml").getFile()));
        chatDirector.loadConfig();
    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(ChatDirector.getConfigStaging().getChains().containsKey("discord-input"));
        assertEquals(7, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().size());
        DiscordInputItem discordInputItem = new DiscordInputItem();
        discordInputItem.channel="0";
        discordInputItem.bot="bot-name";
        discordInputItem.category="CATEGORY_ID";
        discordInputItem.message="MESSAGE_ID";
        discordInputItem.reactionAddEvent=true;
        discordInputItem.reactionRemoveEvent=false;
        discordInputItem.messageEvent=true;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(0));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.category="CATEGORY 2";
        discordInputItem.message="MESSAGE 2";
        discordInputItem.reactionAddEvent=false;
        discordInputItem.reactionRemoveEvent=true;
        discordInputItem.messageEvent=false;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(1));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.category="CATEGORY 2";
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(2));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.message="MESSAGE 2";
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(3));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.reactionAddEvent=false;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(4));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.reactionRemoveEvent=true;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(5));
        discordInputItem = new DiscordInputItem();
        discordInputItem.channel="16";
        discordInputItem.bot="bot2";
        discordInputItem.messageEvent=false;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(6));
    }

    @Test
    public void parseResolve() {
        init();
        assertTrue(ChatDirector.getConfigStaging().getChains().containsKey("discord-resolve"));
        assertEquals(2, ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().size());
        DiscordResolveItem discordInputItem = new DiscordResolveItem();
        discordInputItem.server="0";
        discordInputItem.bot="bot-name";
        discordInputItem.toDiscord=false;
        discordInputItem.toPlain=true;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().get(0));
        discordInputItem = new DiscordResolveItem();
        discordInputItem.server="SERVER";
        discordInputItem.bot="bot";
        discordInputItem.toDiscord=true;
        discordInputItem.toPlain=false;
        assertEquals(discordInputItem, ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().get(1));
    }
}
