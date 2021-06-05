package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.configuration.Configuration;

import org.junit.Test;

public class TestLegacyBukkit {
    ChatDirector chatDirector;

    @Test
    public void v0_2_0() {
        File input = new File(this.getClass().getClassLoader().getResource("legacyConfig/modules/bukkit/v0_2_0/input.yml").getFile());
        File output = new File(this.getClass().getClassLoader().getResource("legacyConfig/modules/bukkit/v0_2_0/output.yml").getFile());
        ChatDirector chatDirector = new ChatDirector(input);
        assertTrue(chatDirector.load());
        Configuration legacyConfiguration = ChatDirector.getConfig();

        chatDirector = new ChatDirector(output);
        assertTrue(chatDirector.load());
        Configuration latestConfiguration = ChatDirector.getConfig();
        System.out.println(legacyConfiguration);
        System.out.println(latestConfiguration);
        assertEquals(legacyConfiguration, latestConfiguration);
    }
}