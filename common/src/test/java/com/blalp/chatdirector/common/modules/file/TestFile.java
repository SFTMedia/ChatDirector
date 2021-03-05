package com.blalp.chatdirector.common.modules.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import com.blalp.chatdirector.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestFile {

    static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/file/config.yml").getFile()));
        try {
            new File("target/PATH_TO_FIFO").createNewFile();
            new File("target/PATH_TO_FIFO_2").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("file-input"));
        assertEquals(3, chatDirector.getChains().get("file-input").getItems().size());
        FileInputItem fileInputItem = new FileInputItem();
        fileInputItem.path = "target/PATH_TO_FIFO";
        fileInputItem.delay = 500;
        fileInputItem.create = true;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(0));
        fileInputItem = new FileInputItem();
        fileInputItem.path = "target/PATH_TO_FIFO_2";
        fileInputItem.create = true;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(1));
        fileInputItem = new FileInputItem();
        fileInputItem.path = "target/PATH_TO_FIFO_2";
        fileInputItem.create = false;
        assertEquals(fileInputItem, chatDirector.getChains().get("file-input").getItems().get(2));
    }

    @Test
    public void parseOutput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("file-output"));
        assertEquals(3, chatDirector.getChains().get("file-output").getItems().size());
        FileOutputItem fileOutputItem = new FileOutputItem();
        fileOutputItem.path = "target/PATH_TO_FIFO";
        fileOutputItem.delay = 500;
        fileOutputItem.create = true;
        assertEquals(fileOutputItem, chatDirector.getChains().get("file-output").getItems().get(0));
        fileOutputItem = new FileOutputItem();
        fileOutputItem.path = "target/PATH_TO_FIFO_2";
        fileOutputItem.create = true;
        assertEquals(fileOutputItem, chatDirector.getChains().get("file-output").getItems().get(1));
        fileOutputItem = new FileOutputItem();
        fileOutputItem.path = "target/PATH_TO_FIFO_2";
        fileOutputItem.create = false;
        assertEquals(fileOutputItem, chatDirector.getChains().get("file-output").getItems().get(2));
    }
}
