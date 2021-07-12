package com.blalp.chatdirector.common.modules.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestConsole {

    static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/console/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parse() {
        init();
        assertTrue(chatDirector.getChains().containsKey("console-test"));
        assertEquals(4, chatDirector.getChains().get("console-test").getItems().size());
        assertEquals(new ConsoleOutputItem(), chatDirector.getChains().get("console-test").getItems().get(0));
        assertEquals(new ConsoleOutputItem(), chatDirector.getChains().get("console-test").getItems().get(1));
        assertEquals(new ConsoleOutputErrorItem(), chatDirector.getChains().get("console-test").getItems().get(2));
        assertEquals(new ConsoleOutputErrorItem(), chatDirector.getChains().get("console-test").getItems().get(3));
    }
}
