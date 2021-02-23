package com.blalp.chatdirector.common.modules.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestContext {

    static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/context/config.yml").getFile()));
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
        // context-parse-test-2
        assertTrue(chatDirector.getChains().containsKey("context-parse-test"));
        assertNotNull(chatDirector.getChains().get("context-parse-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("context-parse-test").items);
        assertTrue(chatDirector.getChains().get("context-parse-test").items.size() == 6);
        assertTrue(chatDirector.getChains().get("context-parse-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("context-parse-test").items.get(0);
        assertTrue(item instanceof ContextGetItem);
        IItem compare = new ContextGetItem();
        ((ContextGetItem) compare).setKey("CONTEXT_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(1);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem) compare).setKey("TARGET_CONTEXT");
        ((ContextSetItem) compare).setValue("CONTEXT_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(2);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem) compare).setKey("TARGET_CONTEXT");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(3);
        assertTrue(item instanceof ContextRemoveItem);
        compare = new ContextRemoveItem();
        ((ContextRemoveItem) compare).setKey("SERVER_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(4);
        assertTrue(item instanceof ContextResolveItem);
        compare = new ContextResolveItem();
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(5);
        assertTrue(item instanceof ContextResolveItem);
        compare = new ContextResolveItem();
        assertEquals(compare, item);
    }

    @Test
    public void integration() {
        init();
        // Checking Chain metric
        assertTrue(chatDirector.getChains().containsKey("context-parse-test"));
        assertNotNull(chatDirector.getChains().get("context-parse-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("context-parse-test").items);
        assertTrue(chatDirector.getChains().get("context-parse-test").items.size() == 6);
        assertTrue(chatDirector.getChains().get("context-parse-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("context-parse-test").items.get(0);
        assertTrue(item instanceof ContextGetItem);
        IItem compare = new ContextGetItem();
        ((ContextGetItem) compare).setKey("CONTEXT_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(1);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem) compare).setKey("TARGET_CONTEXT");
        ((ContextSetItem) compare).setValue("CONTEXT_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(2);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem) compare).setKey("TARGET_CONTEXT");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(3);
        assertTrue(item instanceof ContextRemoveItem);
        compare = new ContextRemoveItem();
        ((ContextRemoveItem) compare).setKey("SERVER_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(4);
        assertTrue(item instanceof ContextResolveItem);
        compare = new ContextResolveItem();
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(5);
        assertTrue(item instanceof ContextResolveItem);
        assertEquals(compare, item);

        // Behavior testing

    }
}
