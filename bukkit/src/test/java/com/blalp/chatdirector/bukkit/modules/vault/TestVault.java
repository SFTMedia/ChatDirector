package com.blalp.chatdirector.bukkit.modules.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestVault {

    private static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/vault/config.yml").getFile()));
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
    public void parseContext() {
        init();
        assertTrue(chatDirector.getChains().containsKey("vault-demo"));
        assertEquals(1, chatDirector.getChains().get("vault-demo").items.size());
        assertEquals(chatDirector.getChains().get("vault-demo").items.get(0), new VaultContextItem());
    }
}
