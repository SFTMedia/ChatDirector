package com.blalp.chatdirector.bungee.modules.multichat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestMultiChat {

    private static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/multichat/config.yml").getFile()));
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
    public void parseContext() {
        init();
        assertTrue(chatDirector.getChains().containsKey("multichat-context"));
        assertEquals(1, chatDirector.getChains().get("multichat-context").items.size());
        assertEquals(chatDirector.getChains().get("multichat-context").items.get(0), new MultiChatContextItem());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("multichat-input"));
        assertEquals(4, chatDirector.getChains().get("multichat-input").items.size());
        MultiChatInputItem multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.broadcast = true;
        multiChatInputItem.global = true;
        multiChatInputItem.staff = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").items.get(0));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.global = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").items.get(1));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.staff = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").items.get(2));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.broadcast = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").items.get(3));
    }
}
