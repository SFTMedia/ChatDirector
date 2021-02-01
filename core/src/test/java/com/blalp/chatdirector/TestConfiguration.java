package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.common.BreakItem;
import com.blalp.chatdirector.modules.common.EchoItem;
import com.blalp.chatdirector.modules.common.HaltItem;
import com.blalp.chatdirector.modules.common.ReloadItem;

import org.junit.jupiter.api.Test;

public class TestConfiguration {
    String rawData = ""+
"# Example of just common items\n"+
"    modules: null\n"+
"    debug: false\n"+
"    chains:\n"+
"      - loading-test: # all of these should load\n"+
"          - break\n"+
"          - break: null\n"+
"          - halt\n"+
"          - halt: null\n"+
"          - echo: \"%CURRENT%\"\n"+
"          - echo: \"raw string\"\n"+
"          - echo: \"\"\n"+
"          - reload\n"+
"          - reload: null\n"+
"      - breaking-test: # This should return second value\n"+
"          - echo: \"This is the first value\"\n"+
"          - echo: \"This is the second value\"\n"+
"          - break\n"+
"          - echo: \"This is the third value\"\n"+
"      - echo-test: # This should echo \"This was >This was >hello!<<, but before that it was >hello!<\"\n"+
"          - echo: \"hello!\"\n"+
"          - echo: \"This was >%CURRENT%<\"\n"+
"          - echo: \"This was >%CURRENT%<, but before that it was >%LAST%<\"\n"+
"      - halt-test: # This should not get to the second value\n"+
"          - echo: \"This is the first value\"\n"+
"          - halt\n"+
"          - echo: \"This is the second value\"\n"+
"      - reload-test: # This should trigger a reload\n"+
"          - reload\n";
    @Test
    public void test(){
        ChatDirector chatDirector = new ChatDirector(rawData);
        chatDirector.load();
        // Checking Chain metrics
        assertTrue(chatDirector.getChains().size()==5);
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
        assertNotNull(chatDirector.getChains().get("loading-test").items);
        assertNotNull(chatDirector.getChains().get("breaking-test").items);
        assertNotNull(chatDirector.getChains().get("echo-test").items);
        assertNotNull(chatDirector.getChains().get("halt-test").items);
        assertNotNull(chatDirector.getChains().get("reload-test").items);
        assertTrue(chatDirector.getChains().get("loading-test").items.size()==9);
        assertTrue(chatDirector.getChains().get("breaking-test").items.size()==4);
        assertTrue(chatDirector.getChains().get("echo-test").items.size()==3);
        assertTrue(chatDirector.getChains().get("halt-test").items.size()==3);
        assertTrue(chatDirector.getChains().get("reload-test").items.size()==1);
        assertTrue(chatDirector.getChains().get("loading-test").isValid());
        assertTrue(chatDirector.getChains().get("breaking-test").isValid());
        assertTrue(chatDirector.getChains().get("echo-test").isValid());
        assertTrue(chatDirector.getChains().get("halt-test").isValid());
        assertTrue(chatDirector.getChains().get("reload-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("loading-test").items.get(0);
        assertTrue(item instanceof BreakItem);
        item = chatDirector.getChains().get("loading-test").items.get(1);
        assertTrue(item instanceof BreakItem);
        item = chatDirector.getChains().get("loading-test").items.get(2);
        assertTrue(item instanceof HaltItem);
        item = chatDirector.getChains().get("loading-test").items.get(3);
        assertTrue(item instanceof HaltItem);
        item = chatDirector.getChains().get("loading-test").items.get(4);
        assertTrue(item instanceof EchoItem);
        assertEquals("%CURRENT%", ((EchoItem)item).format);
        item = chatDirector.getChains().get("loading-test").items.get(5);
        assertTrue(item instanceof EchoItem);
        assertEquals("raw string", ((EchoItem)item).format);
        item = chatDirector.getChains().get("loading-test").items.get(6);
        assertTrue(item instanceof EchoItem);
        assertEquals("", ((EchoItem)item).format);
        item = chatDirector.getChains().get("loading-test").items.get(7);
        assertTrue(item instanceof ReloadItem);
        item = chatDirector.getChains().get("loading-test").items.get(8);
        assertTrue(item instanceof ReloadItem);
        item = chatDirector.getChains().get("breaking-test").items.get(0);
        assertTrue(item instanceof EchoItem);
        assertEquals("This is the first value", ((EchoItem)item).format);
        item = chatDirector.getChains().get("breaking-test").items.get(1);
        assertTrue(item instanceof EchoItem);
        assertEquals("This is the second value", ((EchoItem)item).format);
        item = chatDirector.getChains().get("breaking-test").items.get(2);
        assertTrue(item instanceof BreakItem);
        item = chatDirector.getChains().get("breaking-test").items.get(3);
        assertTrue(item instanceof EchoItem);
        assertEquals("This is the third value", ((EchoItem)item).format);

        item = chatDirector.getChains().get("echo-test").items.get(0);
        assertTrue(item instanceof EchoItem);
        assertEquals("hello!", ((EchoItem)item).format);
        item = chatDirector.getChains().get("echo-test").items.get(1);
        assertTrue(item instanceof EchoItem);
        assertEquals("This was >%CURRENT%<", ((EchoItem)item).format);
        item = chatDirector.getChains().get("echo-test").items.get(2);
        assertTrue(item instanceof EchoItem);
        assertEquals("This was >%CURRENT%<, but before that it was >%LAST%<", ((EchoItem)item).format);

        item = chatDirector.getChains().get("halt-test").items.get(0);
        assertTrue(item instanceof EchoItem);
        assertEquals("This is the first value", ((EchoItem)item).format);
        item = chatDirector.getChains().get("halt-test").items.get(1);
        assertTrue(item instanceof HaltItem);
        item = chatDirector.getChains().get("halt-test").items.get(2);
        assertTrue(item instanceof EchoItem);
        assertEquals("This is the second value", ((EchoItem)item).format);

        item = chatDirector.getChains().get("reload-test").items.get(0);
        assertTrue(item instanceof ReloadItem);
        // TODO: Preform a check for reload?
    }
    @Test
    public void testIntegration() {
        ChatDirector chatDirector = new ChatDirector(rawData);
        chatDirector.load();
        assertEquals(new Context(), chatDirector.getChains().get("loading-test").run(new Context()));
        assertEquals(new Context("This is the second value","This is the first value"), chatDirector.getChains().get("breaking-test").run(new Context()));
        assertEquals(new Context("This was >This was >hello!<<, but before that it was >hello!<","This was >hello!<"), chatDirector.getChains().get("echo-test").run(new Context()));
        assertNotEquals(new Context("This is the first value"), chatDirector.getChains().get("halt-test").run(new Context()));
        assertEquals(new Context("This is the first value").halt(), chatDirector.getChains().get("halt-test").run(new Context()));
        assertEquals(new Context(), chatDirector.getChains().get("reload-test").run(new Context()));
    }
    @Test
    public void testReload(){
        
    }
}
