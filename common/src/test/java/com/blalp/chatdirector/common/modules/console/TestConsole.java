package com.blalp.chatdirector.common.modules.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestConsole {

    static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/console/config.yml").getFile()));
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
