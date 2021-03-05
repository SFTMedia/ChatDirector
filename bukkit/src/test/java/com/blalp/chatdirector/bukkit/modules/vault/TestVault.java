package com.blalp.chatdirector.bukkit.modules.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestVault {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/vault/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseContext() {
        init();
        assertTrue(chatDirector.getChains().containsKey("vault-demo"));
        assertEquals(1, chatDirector.getChains().get("vault-demo").getItems().size());
        assertEquals(chatDirector.getChains().get("vault-demo").getItems().get(0), new VaultContextItem());
    }
}
