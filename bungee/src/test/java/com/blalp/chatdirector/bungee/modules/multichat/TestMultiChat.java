package com.blalp.chatdirector.bungee.modules.multichat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;


import org.junit.jupiter.api.Test;

public class TestMultiChat {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/multichat/config.yml").getFile()));
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
        assertTrue(chatDirector.getChains().containsKey("multichat-context"));
        assertEquals(1, chatDirector.getChains().get("multichat-context").getItems().size());
        assertEquals(chatDirector.getChains().get("multichat-context").getItems().get(0), new MultiChatContextItem());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("multichat-input"));
        assertEquals(4, chatDirector.getChains().get("multichat-input").getItems().size());
        MultiChatInputItem multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.broadcast = true;
        multiChatInputItem.global = true;
        multiChatInputItem.staff = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").getItems().get(0));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.global = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").getItems().get(1));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.staff = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").getItems().get(2));
        multiChatInputItem = new MultiChatInputItem();
        multiChatInputItem.broadcast = true;
        assertEquals(multiChatInputItem, chatDirector.getChains().get("multichat-input").getItems().get(3));
    }
}
