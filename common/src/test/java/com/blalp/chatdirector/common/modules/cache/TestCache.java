package com.blalp.chatdirector.common.modules.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.common.BreakItem;
import com.blalp.chatdirector.modules.common.EchoItem;
import com.blalp.chatdirector.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestCache {

    static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/cache/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parse() {
        init();
        // cache-parse-test
        assertTrue(chatDirector.getChains().containsKey("cache-parse-test"));
        assertNotNull(chatDirector.getChains().get("cache-parse-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("cache-parse-test").getItems());
        assertTrue(chatDirector.getChains().get("cache-parse-test").getItems().size() == 3);
        assertTrue(chatDirector.getChains().get("cache-parse-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("cache-parse-test").getItems().get(0);
        assertTrue(item instanceof CacheGetItem);
        IItem compare = new CacheGetItem();
        ((CacheGetItem) compare).setKey("SomeUniqueKey");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-parse-test").getItems().get(1);
        assertTrue(item instanceof CacheSetItem);
        compare = new CacheSetItem();
        ((CacheSetItem) compare).setKey("EXAMPLE_KEY");
        ((CacheSetItem) compare).setValue("EXAMPLE_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-parse-test").getItems().get(2);
        assertTrue(item instanceof CacheIfItem);
        compare = new CacheIfItem();
        ((CacheIfItem) compare).setKey("KEY");
        Chain chain = new Chain();
        IItem chainItem = new HaltItem();
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setYesChain(chain);
        chain = new Chain();
        chainItem = new BreakItem();
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setNoChain(chain);
        assertEquals(compare, item);
    }

    @Test
    public void cacheGetSetTest() {
        init();
        // cache-get-set-test
        assertTrue(chatDirector.getChains().containsKey("cache-get-set-test"));
        assertNotNull(chatDirector.getChains().get("cache-get-set-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("cache-get-set-test").getItems());
        assertTrue(chatDirector.getChains().get("cache-get-set-test").getItems().size() == 2);
        assertTrue(chatDirector.getChains().get("cache-get-set-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("cache-get-set-test").getItems().get(0);
        assertTrue(item instanceof CacheSetItem);
        IItem compare = new CacheSetItem();
        ((CacheSetItem) compare).setKey("SomeUniqueKey");
        ((CacheSetItem) compare).setValue("EXAMPLE_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-get-set-test").getItems().get(1);
        assertTrue(item instanceof CacheGetItem);
        compare = new CacheGetItem();
        ((CacheGetItem) compare).setKey("SomeUniqueKey");
        assertEquals(compare, item);
    }

    @Test
    public void cacheGetSetTest2() {
        init();
        // cache-get-set-test-2-2
        assertTrue(chatDirector.getChains().containsKey("cache-get-set-test-2"));
        assertNotNull(chatDirector.getChains().get("cache-get-set-test-2"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("cache-get-set-test-2").getItems());
        assertTrue(chatDirector.getChains().get("cache-get-set-test-2").getItems().size() == 2);
        assertTrue(chatDirector.getChains().get("cache-get-set-test-2").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("cache-get-set-test-2").getItems().get(0);
        assertTrue(item instanceof CacheSetItem);
        IItem compare = new CacheSetItem();
        ((CacheSetItem) compare).setKey("SomeUniqueKey");
        ((CacheSetItem) compare).setValue("EXAMPLE_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-get-set-test-2").getItems().get(1);
        assertTrue(item instanceof CacheGetItem);
        compare = new CacheGetItem();
        ((CacheGetItem) compare).setKey("RandomKey");
        assertEquals(compare, item);
    }

    @Test
    public void cacheIfTest() {
        init();
        // cache-if-test
        assertTrue(chatDirector.getChains().containsKey("cache-if-test"));
        assertNotNull(chatDirector.getChains().get("cache-if-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("cache-if-test").getItems());
        assertTrue(chatDirector.getChains().get("cache-if-test").getItems().size() == 3);
        assertTrue(chatDirector.getChains().get("cache-if-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("cache-if-test").getItems().get(0);
        assertTrue(item instanceof CacheSetItem);
        IItem compare = new CacheSetItem();
        ((CacheSetItem) compare).setKey("SomeUniqueKey");
        ((CacheSetItem) compare).setValue("EXAMPLE_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-if-test").getItems().get(1);
        assertTrue(item instanceof CacheIfItem);
        compare = new CacheIfItem();
        Chain chain = new Chain();
        IItem chainItem = new EchoItem("SomeUniqueKey was found!");
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setYesChain(chain);
        chain = new Chain();
        chainItem = new EchoItem("SomeUniqueKey was not found!");
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setNoChain(chain);
        ((CacheIfItem) compare).setKey("SomeUniqueKey");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-if-test").getItems().get(2);
        assertTrue(item instanceof CacheIfItem);
        compare = new CacheIfItem();
        chain = new Chain();
        chainItem = new EchoItem("Random was found!");
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setYesChain(chain);
        chain = new Chain();
        chainItem = new EchoItem("Random was not found!");
        chain.addItem(chainItem);
        ((CacheIfItem) compare).setNoChain(chain);
        ((CacheIfItem) compare).setKey("Random");
        assertEquals(compare, item);
    }

    @Test
    public void integration() {
        init();
        // Integration test
        assertEquals(new Context(), chatDirector.getChains().get("cache-parse-test").run(new Context()));
        Context context = new Context();
        context.put("CURRENT", "EXAMPLE_VALUE");
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test").run(new Context()));
        context.put("LAST", "Even if there is something here");
        assertEquals(context,
                chatDirector.getChains().get("cache-get-set-test").run(new Context("Even if there is something here")));
        context = new Context();
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test-2").run(new Context()));
        context.put("CURRENT", "Even if there is something here");
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test-2")
                .run(new Context("Even if there is something here")));
        context = new Context();
        context.put("CURRENT", "Random was not found!");
        context.put("LAST", "SomeUniqueKey was found!");
        assertEquals(context, chatDirector.getChains().get("cache-if-test").run(new Context()));
        assertEquals(context,
                chatDirector.getChains().get("cache-if-test").run(new Context("Even if there was something here.")));
    }
}
