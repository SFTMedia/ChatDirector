package com.blalp.chatdirector.modules.javacord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestJavacord {

    static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/file/config.yml").getFile()));
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
/*
    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("file-input"));
        assertEquals(3, chatDirector.getChains().get("file-input").getItems().size());
        FileInputItem fileInputItem = new FileInputItem();
        fileInputItem.path="target/PATH_TO_FIFO";
        fileInputItem.delay=500;
        fileInputItem.create=true;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(0));
        fileInputItem = new FileInputItem();
        fileInputItem.path="target/PATH_TO_FIFO_2";
        fileInputItem.create=true;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(1));
        fileInputItem = new FileInputItem();
        fileInputItem.path="target/PATH_TO_FIFO_2";
        fileInputItem.create=false;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(2));
    }
    */
}
