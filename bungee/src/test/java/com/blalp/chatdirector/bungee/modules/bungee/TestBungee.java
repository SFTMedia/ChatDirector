package com.blalp.chatdirector.bungee.modules.bungee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.minecraft.model.FancyMessage;
import com.blalp.chatdirector.minecraft.utils.FancyMessageEnum;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestBungee {

    private static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/bungee/config.yml").getFile()));
        chatDirector.load();

    }

    @Test
    public void valid() {
        init();
        for (IModule module : ChatDirector.getConfig().getModules()) {
            assertTrue(module.isValid());
        }
        for (Chain chain : ChatDirector.getConfig().getChains().values()) {
            assertTrue(chain.isValid());
        }
    }

    @Test
    public void parseCommand() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-command"));
        assertEquals(2, chatDirector.getChains().get("bungee-command").getItems().size());
        BungeeCommandItem bungeeInputItem = new BungeeCommandItem();
        bungeeInputItem.setCommand("command");
        bungeeInputItem.setPermission("*");
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-command").getItems().get(0));
        bungeeInputItem = new BungeeCommandItem();
        bungeeInputItem.setCommand("command 2");
        bungeeInputItem.setPermission("permission");
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-command").getItems().get(1));
    }

    @Test
    public void parsePlayerlist() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-playerlist"));
        assertEquals(7, chatDirector.getChains().get("bungee-playerlist").getItems().size());
        BungeePlayerlistItem bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.setFormat("```\nOnline players (%NUM_PLAYERS%/%MAX_PLAYERS%):\n");
        bungeePlayerlistItem.formatPlayer = "%PLAYER_NAME%";
        bungeePlayerlistItem.formatServer = "%SERVER_NAME%";
        bungeePlayerlistItem.formatNoPlayers = "**No online players**";
        bungeePlayerlistItem.splitByServer = false;
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(0));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(1));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.format = "format";
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(2));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.formatPlayer = "player";
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(3));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.formatServer = "server";
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(4));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.formatNoPlayers = "no one";
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(5));
        bungeePlayerlistItem = new BungeePlayerlistItem();
        bungeePlayerlistItem.splitByServer = true;
        assertEquals(bungeePlayerlistItem, chatDirector.getChains().get("bungee-playerlist").getItems().get(6));
    }

    @Test
    public void parseOutput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-output"));
        assertEquals(1, chatDirector.getChains().get("bungee-output").getItems().size());
        BungeeOutputItem bungeeOutputItem = new BungeeOutputItem();
        assertEquals(bungeeOutputItem, chatDirector.getChains().get("bungee-output").getItems().get(0));
    }

    @Test
    public void parseOutputFancy() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-output-fancy"));
        assertEquals(2, chatDirector.getChains().get("bungee-output-fancy").getItems().size());
        BungeeOutputFancyItem bungeeOutputFancy = new BungeeOutputFancyItem();
        FancyMessage[] fancyMessages = new FancyMessage[6];
        fancyMessages[0] = new FancyMessage();
        fancyMessages[0].setClickEvent(FancyMessageEnum.RUN_COMMAND, "/test");
        fancyMessages[0].setText("click command");
        fancyMessages[1] = new FancyMessage();
        fancyMessages[1].setClickEvent(FancyMessageEnum.SUGGEST_COMMAND, "/suggestion");
        fancyMessages[1].setText("click suggestion");
        fancyMessages[2] = new FancyMessage();
        fancyMessages[2].setClickEvent(FancyMessageEnum.OPEN_URL, "https://sftmc.org");
        fancyMessages[2].setText("click url");
        fancyMessages[3] = new FancyMessage();
        fancyMessages[3].setColor("RED");
        fancyMessages[3].setBold(false);
        fancyMessages[3].setItalics(true);
        fancyMessages[3].setStrikethrough(true);
        fancyMessages[3].setObfuscated(false);
        fancyMessages[3].setText("TEST");
        fancyMessages[4] = new FancyMessage();
        fancyMessages[4].setHoverEvent(FancyMessageEnum.SHOW_TEXT, "text");
        fancyMessages[4].setText("TEST");
        fancyMessages[5] = new FancyMessage();
        fancyMessages[5].setText("TEST");
        bungeeOutputFancy.setPlayer("player");
        bungeeOutputFancy.sendToCurrentServer = true;
        FancyMessage fancyMessage = new FancyMessage();
        for (FancyMessage message : fancyMessages) {
            fancyMessage.append(message);
        }
        bungeeOutputFancy.setFancyMessage(fancyMessage);
        assertEquals(bungeeOutputFancy, chatDirector.getChains().get("bungee-output-fancy").getItems().get(0));
        bungeeOutputFancy = new BungeeOutputFancyItem();
        ArrayList<FancyMessage> nested = new ArrayList<>();
        FancyMessage child = new FancyMessage();
        child.setColor("BLUE");
        child.setBold(true);
        child.setItalics(false);
        child.setStrikethrough(false);
        child.setObfuscated(true);
        child.setText("I am blue");
        nested.add(child);
        fancyMessages = new FancyMessage[2];
        fancyMessages[0] = new FancyMessage();
        fancyMessages[0].setHoverEvent(FancyMessageEnum.SHOW_TEXT, "hover");
        fancyMessages[0].setNext(nested);
        fancyMessages[1] = new FancyMessage();
        fancyMessages[1].setText("RAW");
        bungeeOutputFancy.setPermission("test");
        bungeeOutputFancy.sendToCurrentServer = false;
        fancyMessage = new FancyMessage();
        for (FancyMessage message : fancyMessages) {
            fancyMessage.append(message);
        }
        bungeeOutputFancy.setFancyMessage(fancyMessage);
        assertEquals(bungeeOutputFancy, chatDirector.getChains().get("bungee-output-fancy").getItems().get(1));
    }

    @Test
    public void parseOutputServer() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-output-server"));
        assertEquals(2, chatDirector.getChains().get("bungee-output-server").getItems().size());
        BungeeOutputServerItem bungeeOutputServerItem = new BungeeOutputServerItem();
        bungeeOutputServerItem.server = "SERVER NAME";
        assertEquals(bungeeOutputServerItem, chatDirector.getChains().get("bungee-output-server").getItems().get(0));
        bungeeOutputServerItem = new BungeeOutputServerItem();
        bungeeOutputServerItem.server = "Test";
        bungeeOutputServerItem.permission = "permission";
        assertEquals(bungeeOutputServerItem, chatDirector.getChains().get("bungee-output-server").getItems().get(1));
    }

    @Test
    public void parseOutputPlayer() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-output-player"));
        assertEquals(2, chatDirector.getChains().get("bungee-output-player").getItems().size());
        BungeeOutputPlayerItem bungeeOutputPlayerItem = new BungeeOutputPlayerItem();
        bungeeOutputPlayerItem.player = "%PLAYER_NAME%";
        bungeeOutputPlayerItem.permission = null;
        assertEquals(bungeeOutputPlayerItem, chatDirector.getChains().get("bungee-output-player").getItems().get(0));
        bungeeOutputPlayerItem = new BungeeOutputPlayerItem();
        bungeeOutputPlayerItem.player = "test";
        bungeeOutputPlayerItem.permission = "test";
        assertEquals(bungeeOutputPlayerItem, chatDirector.getChains().get("bungee-output-player").getItems().get(1));
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("bungee-input"));
        assertEquals(5, chatDirector.getChains().get("bungee-input").getItems().size());
        BungeeInputItem bungeeInputItem = new BungeeInputItem();
        bungeeInputItem.chat = true;
        bungeeInputItem.disconnect = true;
        bungeeInputItem.switchServers = true;
        bungeeInputItem.switchServers = true;
        bungeeInputItem.join = true;
        bungeeInputItem.overrideChat = true;
        bungeeInputItem.command = false;
        bungeeInputItem.formatChat = "[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
        bungeeInputItem.formatDisconnect = "&0[&4<-&0] &e%PLAYER_NAME% has left the network from %PLAYER_SERVER_NAME%, Have a good day!";
        bungeeInputItem.formatJoin = "&0[&2->&0] &e%PLAYER_NAME% joined the network on %PLAYER_SERVER_NAME%!";
        bungeeInputItem.formatSwitchServers = "&0[&e<->&0] &e%PLAYER_NAME% has switched to %PLAYER_SERVER_NAME%";
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-input").getItems().get(0));
        bungeeInputItem = new BungeeInputItem();
        bungeeInputItem.disconnect = true;
        bungeeInputItem.switchServers = true;
        bungeeInputItem.switchServers = true;
        bungeeInputItem.join = true;
        bungeeInputItem.overrideChat = true;
        bungeeInputItem.command = false;
        bungeeInputItem.formatChat = "form 1";
        bungeeInputItem.formatDisconnect = "dc 1";
        bungeeInputItem.formatJoin = "join";
        bungeeInputItem.formatSwitchServers = "switch";
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-input").getItems().get(1));
        bungeeInputItem = new BungeeInputItem();
        bungeeInputItem.switchServers = true;
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-input").getItems().get(2));
        bungeeInputItem = new BungeeInputItem();
        bungeeInputItem.join = true;
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-input").getItems().get(3));
        bungeeInputItem = new BungeeInputItem();
        bungeeInputItem.command = true;
        assertEquals(bungeeInputItem, chatDirector.getChains().get("bungee-input").getItems().get(4));

    }
}
