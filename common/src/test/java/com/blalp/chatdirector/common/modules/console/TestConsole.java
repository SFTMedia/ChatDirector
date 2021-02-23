package com.blalp.chatdirector.common.modules.console;

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
        for (IModule module : ChatDirector.getConfig().modules) {
            assertTrue(module.isValid());
        }
        for (Chain chain : ChatDirector.getConfig().chains.values()) {
            assertTrue(chain.isValid());
        }
    }

    @Test
    public void parse() {
        init();

    }

    @Test
    public void integration() {
        init();

    }
}
