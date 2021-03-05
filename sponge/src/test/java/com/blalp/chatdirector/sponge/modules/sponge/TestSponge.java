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
    /*
        Other tests
    */
}
