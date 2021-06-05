package com.blalp.chatdirector.bukkit.modules.bukkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.bukkit.modules.placeholderapi.PlaceholderAPIResolveItem;
import com.blalp.chatdirector.core.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestBukkit {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/bukkit/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bukkit-input"));
        assertEquals(12, chatDirector.getChains().get("bukkit-input").getItems().size());
        BukkitInputItem bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = false;
        bukkitInputItem.logout = true;
        bukkitInputItem.login = true;
        bukkitInputItem.serverStarted = true;
        bukkitInputItem.serverStopped = true;
        bukkitInputItem.checkCanceled = true;
        bukkitInputItem.overrideChat = true;
        bukkitInputItem.newPlayer = true;
        bukkitInputItem.cancelChat = true;
        bukkitInputItem.formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
        bukkitInputItem.formatLogin = "login";
        bukkitInputItem.formatNewPlayer = "new player";
        bukkitInputItem.formatLogout = "logout";
        bukkitInputItem.formatStopped = "stopped";
        bukkitInputItem.formatStarted = "started";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(0));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = true;
        bukkitInputItem.logout = false;
        bukkitInputItem.login = false;
        bukkitInputItem.serverStarted = false;
        bukkitInputItem.serverStopped = false;
        bukkitInputItem.checkCanceled = false;
        bukkitInputItem.overrideChat = false;
        bukkitInputItem.newPlayer = false;
        bukkitInputItem.cancelChat = false;
        bukkitInputItem.formatChat = "%PLAYER_NAME%: %Test%";
        bukkitInputItem.formatLogin = "login 2";
        bukkitInputItem.formatNewPlayer = "new player 2";
        bukkitInputItem.formatLogout = "logout 2";
        bukkitInputItem.formatStopped = "stopped 2";
        bukkitInputItem.formatStarted = "started 2";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(1));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = true;
        bukkitInputItem.formatChat = "formatting";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(2));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.login = true;
        bukkitInputItem.formatLogin = "login 3";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(3));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.login = true;
        bukkitInputItem.formatNewPlayer = "new player 3";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(4));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.logout = true;
        bukkitInputItem.formatLogout = "logout 3";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(5));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.serverStarted = true;
        bukkitInputItem.formatStarted = "started 3";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(6));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.serverStopped = true;
        bukkitInputItem.formatStopped = "stopped 3";
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(7));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = true;
        bukkitInputItem.checkCanceled = true;
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(8));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = true;
        bukkitInputItem.overrideChat = true;
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(9));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.login = true;
        bukkitInputItem.newPlayer = true;
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(10));
        bukkitInputItem = new BukkitInputItem();
        bukkitInputItem.chat = true;
        bukkitInputItem.cancelChat = true;
        assertEquals(bukkitInputItem, chatDirector.getChains().get("bukkit-input").getItems().get(11));
    }

    @Test
    public void parsePlayerlist() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bukkit-playerlist"));
        assertEquals(6, chatDirector.getChains().get("bukkit-playerlist").getItems().size());
        BukkitPlayerlistItem bukkitPlayerlistItem = new BukkitPlayerlistItem();
        bukkitPlayerlistItem.format = "format 1";
        bukkitPlayerlistItem.formatPlayer = "player 1";
        bukkitPlayerlistItem.formatNoPlayers = "no players 1";
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(0));
        bukkitPlayerlistItem = new BukkitPlayerlistItem();
        bukkitPlayerlistItem.formatPlayer = "player 2";
        bukkitPlayerlistItem.formatNoPlayers = "no players 2";
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(1));
        bukkitPlayerlistItem = new BukkitPlayerlistItem();
        bukkitPlayerlistItem.formatNoPlayers = "no players 3";
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(2));
        bukkitPlayerlistItem = new BukkitPlayerlistItem();
        bukkitPlayerlistItem.format = "format 4";
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(3));
        bukkitPlayerlistItem = new BukkitPlayerlistItem();
        bukkitPlayerlistItem.formatPlayer = "player 5";
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(4));
        bukkitPlayerlistItem = new BukkitPlayerlistItem();
        assertEquals(bukkitPlayerlistItem, chatDirector.getChains().get("bukkit-playerlist").getItems().get(5));
    }

    @Test
    public void parseOutput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bukkit-output"));
        assertEquals(2, chatDirector.getChains().get("bukkit-output").getItems().size());
        BukkitOutputItem bukkitOutputItem = new BukkitOutputItem();
        bukkitOutputItem.permission = "permission test";
        assertEquals(bukkitOutputItem, chatDirector.getChains().get("bukkit-output").getItems().get(0));
        bukkitOutputItem = new BukkitOutputItem();
        assertEquals(bukkitOutputItem, chatDirector.getChains().get("bukkit-output").getItems().get(1));
    }

    @Test
    public void parseCommand() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bukkit-command"));
        assertEquals(1, chatDirector.getChains().get("bukkit-command").getItems().size());
        BukkitCommandItem bukkitCommandItem = new BukkitCommandItem();
        bukkitCommandItem.setCommand("example-command");
        bukkitCommandItem.setPermission("example-permission");
        assertEquals(bukkitCommandItem, chatDirector.getChains().get("bukkit-command").getItems().get(0));
    }

    @Test
    public void parsePlaceholderAPIResolve() {
        init();
        assertTrue(chatDirector.getChains().containsKey("placeholderapi-resolve"));
        assertEquals(1, chatDirector.getChains().get("placeholderapi-resolve").getItems().size());
        PlaceholderAPIResolveItem bukkitCommandItem = new PlaceholderAPIResolveItem();
        assertEquals(bukkitCommandItem, chatDirector.getChains().get("placeholderapi-resolve").getItems().get(0));
    }
}
