package com.blalp.chatdirector.core.modules.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import org.junit.jupiter.api.Test;

public class TestConfiguration {
        @Test
        public void test() {
                ChatDirector chatDirector = new ChatDirector(new File(
                                this.getClass().getClassLoader().getResource("modules/common/config.yml").getFile()));

                assertTrue(chatDirector.load());
                // Checking Chain metrics
                assertTrue(chatDirector.getChains().size() == 5);
                assertTrue(chatDirector.getChains().containsKey("loading-test"));
                assertTrue(chatDirector.getChains().containsKey("breaking-test"));
                assertTrue(chatDirector.getChains().containsKey("echo-test"));
                assertTrue(chatDirector.getChains().containsKey("halt-test"));
                assertTrue(chatDirector.getChains().containsKey("reload-test"));
                assertNotNull(chatDirector.getChains().get("loading-test"));
                assertNotNull(chatDirector.getChains().get("breaking-test"));
                assertNotNull(chatDirector.getChains().get("echo-test"));
                assertNotNull(chatDirector.getChains().get("halt-test"));
                assertNotNull(chatDirector.getChains().get("reload-test"));
                // Checking Per Chain metrics
                assertNotNull(chatDirector.getChains().get("loading-test").getItems());
                assertNotNull(chatDirector.getChains().get("breaking-test").getItems());
                assertNotNull(chatDirector.getChains().get("echo-test").getItems());
                assertNotNull(chatDirector.getChains().get("halt-test").getItems());
                assertNotNull(chatDirector.getChains().get("reload-test").getItems());
                assertTrue(chatDirector.getChains().get("loading-test").getItems().size() == 9);
                assertTrue(chatDirector.getChains().get("breaking-test").getItems().size() == 4);
                assertTrue(chatDirector.getChains().get("echo-test").getItems().size() == 3);
                assertTrue(chatDirector.getChains().get("halt-test").getItems().size() == 3);
                assertTrue(chatDirector.getChains().get("reload-test").getItems().size() == 1);
                assertTrue(chatDirector.getChains().get("loading-test").isValid());
                assertTrue(chatDirector.getChains().get("breaking-test").isValid());
                assertTrue(chatDirector.getChains().get("echo-test").isValid());
                assertTrue(chatDirector.getChains().get("halt-test").isValid());
                assertTrue(chatDirector.getChains().get("reload-test").isValid());
                // Checking Each item in chain
                IItem item = chatDirector.getChains().get("loading-test").getItems().get(0);
                assertTrue(item instanceof BreakItem);
                item = chatDirector.getChains().get("loading-test").getItems().get(1);
                assertTrue(item instanceof BreakItem);
                item = chatDirector.getChains().get("loading-test").getItems().get(2);
                assertTrue(item instanceof HaltItem);
                item = chatDirector.getChains().get("loading-test").getItems().get(3);
                assertTrue(item instanceof HaltItem);
                item = chatDirector.getChains().get("loading-test").getItems().get(4);
                assertTrue(item instanceof EchoItem);
                assertEquals("%CURRENT%", ((EchoItem) item).format);
                item = chatDirector.getChains().get("loading-test").getItems().get(5);
                assertTrue(item instanceof EchoItem);
                assertEquals("raw string", ((EchoItem) item).format);
                item = chatDirector.getChains().get("loading-test").getItems().get(6);
                assertTrue(item instanceof EchoItem);
                assertEquals("", ((EchoItem) item).format);
                item = chatDirector.getChains().get("loading-test").getItems().get(7);
                assertTrue(item instanceof ReloadItem);
                item = chatDirector.getChains().get("loading-test").getItems().get(8);
                assertTrue(item instanceof ReloadItem);
                item = chatDirector.getChains().get("breaking-test").getItems().get(0);
                assertTrue(item instanceof EchoItem);
                assertEquals("This is the first value", ((EchoItem) item).format);
                item = chatDirector.getChains().get("breaking-test").getItems().get(1);
                assertTrue(item instanceof EchoItem);
                assertEquals("This is the second value", ((EchoItem) item).format);
                item = chatDirector.getChains().get("breaking-test").getItems().get(2);
                assertTrue(item instanceof BreakItem);
                item = chatDirector.getChains().get("breaking-test").getItems().get(3);
                assertTrue(item instanceof EchoItem);
                assertEquals("This is the third value", ((EchoItem) item).format);

                item = chatDirector.getChains().get("echo-test").getItems().get(0);
                assertTrue(item instanceof EchoItem);
                assertEquals("hello!", ((EchoItem) item).format);
                item = chatDirector.getChains().get("echo-test").getItems().get(1);
                assertTrue(item instanceof EchoItem);
                assertEquals("This was >%CURRENT%<", ((EchoItem) item).format);
                item = chatDirector.getChains().get("echo-test").getItems().get(2);
                assertTrue(item instanceof EchoItem);
                assertEquals("This was >%CURRENT%<, but before that it was >%LAST%<", ((EchoItem) item).format);

                item = chatDirector.getChains().get("halt-test").getItems().get(0);
                assertTrue(item instanceof EchoItem);
                assertEquals("This is the first value", ((EchoItem) item).format);
                item = chatDirector.getChains().get("halt-test").getItems().get(1);
                assertTrue(item instanceof HaltItem);
                item = chatDirector.getChains().get("halt-test").getItems().get(2);
                assertTrue(item instanceof EchoItem);
                assertEquals("This is the second value", ((EchoItem) item).format);

                item = chatDirector.getChains().get("reload-test").getItems().get(0);
                assertTrue(item instanceof ReloadItem);
                // TODO: Preform a check for reload?
        }

        @Test
        public void testIntegration() {
                ChatDirector chatDirector = new ChatDirector(new File(
                                this.getClass().getClassLoader().getResource("modules/common/config.yml").getFile()));

                assertTrue(chatDirector.load());
                assertEquals(new Context(), chatDirector.getChains().get("loading-test").run(new Context()));
                assertEquals(new Context("This is the second value", "This is the first value"),
                                chatDirector.getChains().get("breaking-test").run(new Context()));
                assertEquals(new Context("This was >This was >hello!<<, but before that it was >hello!<",
                                "This was >hello!<"), chatDirector.getChains().get("echo-test").run(new Context()));
                assertNotEquals(new Context("This is the first value"),
                                chatDirector.getChains().get("halt-test").run(new Context()));
                assertEquals(new Context("This is the first value").halt(),
                                chatDirector.getChains().get("halt-test").run(new Context()));
                assertEquals(new Context(), chatDirector.getChains().get("reload-test").run(new Context()));
        }

        @Test
        public void testReload() {

        }
}
