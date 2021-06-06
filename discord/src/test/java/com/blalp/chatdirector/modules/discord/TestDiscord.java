package com.blalp.chatdirector.modules.discord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.discord.modules.discord.DiscordInputItem;
import com.blalp.chatdirector.discord.modules.discord.DiscordResolveItem;

import org.junit.jupiter.api.Test;

public class TestDiscord {

        static ChatDirector chatDirector;

        @Test
        public void init() {
                if (chatDirector != null) {
                        return;
                }
                chatDirector = new ChatDirector(new File(
                                this.getClass().getClassLoader().getResource("modules/javacord/config.yml").getFile()));
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
                discordInputItem.setChannel("0");
                discordInputItem.setBot("bot-name");
                discordInputItem.setCategory("CATEGORY_ID");
                discordInputItem.setMessage("MESSAGE_ID");
                discordInputItem.setReactionAddEvent(true);
                discordInputItem.setReactionRemoveEvent(false);
                discordInputItem.setMessageEvent(true);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(0));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setCategory("CATEGORY 2");
                discordInputItem.setMessage("MESSAGE 2");
                discordInputItem.setReactionAddEvent(false);
                discordInputItem.setReactionRemoveEvent(true);
                discordInputItem.setMessageEvent(false);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(1));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setCategory("CATEGORY 2");
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(2));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setMessage("MESSAGE 2");
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(3));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setReactionAddEvent(false);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(4));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setReactionRemoveEvent(true);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(5));
                discordInputItem = new DiscordInputItem();
                discordInputItem.setChannel("16");
                discordInputItem.setBot("bot2");
                discordInputItem.setMessageEvent(false);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-input").getItems().get(6));
        }

        @Test
        public void parseResolve() {
                init();
                assertTrue(ChatDirector.getConfigStaging().getChains().containsKey("discord-resolve"));
                assertEquals(2, ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().size());
                DiscordResolveItem discordInputItem = new DiscordResolveItem();
                discordInputItem.setServer("0");
                discordInputItem.setBot("bot-name");
                discordInputItem.setToDiscord(false);
                discordInputItem.setToPlain(true);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().get(0));
                discordInputItem = new DiscordResolveItem();
                discordInputItem.setServer("SERVER");
                discordInputItem.setBot("bot");
                discordInputItem.setToDiscord(true);
                discordInputItem.setToPlain(false);
                assertEquals(discordInputItem,
                                ChatDirector.getConfigStaging().getChains().get("discord-resolve").getItems().get(1));
        }
}
