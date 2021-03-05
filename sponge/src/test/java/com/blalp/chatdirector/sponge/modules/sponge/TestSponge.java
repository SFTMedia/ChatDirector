package com.blalp.chatdirector.sponge.modules.sponge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestSponge {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/sponge/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseCommand() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sponge-command"));
        assertEquals(3, chatDirector.getChains().get("sponge-command").getItems().size());
        SpongeCommandItem spongeCommand = new SpongeCommandItem();
        spongeCommand.command="command";
        spongeCommand.permission="*";
        spongeCommand.args=true;
        assertEquals(spongeCommand, chatDirector.getChains().get("sponge-command").getItems().get(0));
        spongeCommand = new SpongeCommandItem();
        spongeCommand.command="command 2";
        spongeCommand.permission="permission";
        spongeCommand.args=false;
        assertEquals(spongeCommand, chatDirector.getChains().get("sponge-command").getItems().get(1));
        spongeCommand = new SpongeCommandItem();
        spongeCommand.command="command2";
        spongeCommand.permission="permission2";
        assertEquals(spongeCommand, chatDirector.getChains().get("sponge-command").getItems().get(2));
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sponge-input"));
        assertEquals(10, chatDirector.getChains().get("sponge-input").getItems().size());
        SpongeInputItem spongeInputItem = new SpongeInputItem();
        spongeInputItem.chat = false;
        spongeInputItem.logout = true;
        spongeInputItem.login = true;
        spongeInputItem.serverStarted = true;
        spongeInputItem.serverStopped = true;
        spongeInputItem.checkCanceled = true;
        spongeInputItem.overrideChat = true;
        spongeInputItem.cancelChat = true;
        spongeInputItem.formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
        spongeInputItem.formatLogin = "login";
        spongeInputItem.formatLogout = "logout";
        spongeInputItem.formatStopped = "stopped";
        spongeInputItem.formatStarted = "started";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(0));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.chat = true;
        spongeInputItem.logout = false;
        spongeInputItem.login = false;
        spongeInputItem.serverStarted = false;
        spongeInputItem.serverStopped = false;
        spongeInputItem.checkCanceled = false;
        spongeInputItem.overrideChat = false;
        spongeInputItem.cancelChat = false;
        spongeInputItem.formatChat = "%PLAYER_NAME%: %Test%";
        spongeInputItem.formatLogin = "login 2";
        spongeInputItem.formatLogout = "logout 2";
        spongeInputItem.formatStopped = "stopped 2";
        spongeInputItem.formatStarted = "started 2";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(1));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.chat = true;
        spongeInputItem.formatChat = "formatting";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(2));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.login = true;
        spongeInputItem.formatLogin = "login 3";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(3));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.login = true;
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(4));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.logout = true;
        spongeInputItem.formatLogout = "logout 3";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(5));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.serverStarted = true;
        spongeInputItem.formatStarted = "started 3";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(6));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.serverStopped = true;
        spongeInputItem.formatStopped = "stopped 3";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(7));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.chat = true;
        spongeInputItem.checkCanceled = true;
        spongeInputItem.overrideChat =true;
        spongeInputItem.cancelChat=true;
        spongeInputItem.formatChat="chat 2";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(8));
        spongeInputItem = new SpongeInputItem();
        spongeInputItem.chat = true;
        spongeInputItem.checkCanceled = false;
        spongeInputItem.overrideChat =false;
        spongeInputItem.cancelChat=false;
        spongeInputItem.formatChat="chat 3";
        assertEquals(spongeInputItem, chatDirector.getChains().get("sponge-input").getItems().get(9));
    }

    @Test
    public void parsePlayerlist() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sponge-playerlist"));
        assertEquals(4, chatDirector.getChains().get("sponge-playerlist").getItems().size());
        SpongePlayerlistItem spongePlayerlistItem = new SpongePlayerlistItem();
        spongePlayerlistItem.format = "format 1";
        spongePlayerlistItem.formatPlayer = "player 1";
        spongePlayerlistItem.formatNoPlayers = "no players 1";
        assertEquals(spongePlayerlistItem, chatDirector.getChains().get("sponge-playerlist").getItems().get(0));
        spongePlayerlistItem = new SpongePlayerlistItem();
        spongePlayerlistItem.format = "format";
        assertEquals(spongePlayerlistItem, chatDirector.getChains().get("sponge-playerlist").getItems().get(1));
        spongePlayerlistItem = new SpongePlayerlistItem();
        spongePlayerlistItem.formatPlayer="player";
        assertEquals(spongePlayerlistItem, chatDirector.getChains().get("sponge-playerlist").getItems().get(2));
        spongePlayerlistItem = new SpongePlayerlistItem();
        spongePlayerlistItem.formatNoPlayers = "no players";
        assertEquals(spongePlayerlistItem, chatDirector.getChains().get("sponge-playerlist").getItems().get(3));
    }

    @Test
    public void parseOutput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sponge-output"));
        assertEquals(2, chatDirector.getChains().get("sponge-output").getItems().size());
        SpongeOutputItem spongeOutputItem = new SpongeOutputItem();
        spongeOutputItem.permission = null;
        assertEquals(spongeOutputItem, chatDirector.getChains().get("sponge-output").getItems().get(0));
        spongeOutputItem = new SpongeOutputItem();
        spongeOutputItem.permission="permission";
        assertEquals(spongeOutputItem, chatDirector.getChains().get("sponge-output").getItems().get(1));
    }
}
