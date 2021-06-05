package com.blalp.chatdirector.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TestContext {
    @Test
    public void testContext() {
        Context one = new Context();
        Context two = new Context();
        assertEquals(one.hashCode(), two.hashCode());
        assertEquals(one, two);
        one.put("CURRENT", "TEST");
        assertNotEquals(one, two);
        two.put("CURRENT", "TEST");
        assertEquals(one, two);
        one.put("ANOTHER_KEY", "VALUE");
        assertNotEquals(one, two);
        one.remove("ANOTHER_KEY");
        assertNotEquals(one, two);
        two.remove("ANOTHER_KEY");
        assertEquals(one.getCurrent(), two.getCurrent());
        assertEquals(one, two);
        one.halt();
        assertNotEquals(one, two);
        two.halt();
        assertEquals(one, two);
        one = new Context();
        one.put("CURRENT", "TEST2");
        two = new Context("TEST2");
        assertEquals(one, two);
    }

    @Test
    public void testContextMerge() {
        Context one = new Context();
        Context two = new Context();
        one.put("ONE", "1");
        two.put("TWO", "2");
        assertNotEquals(one, two);
        one.merge(two);
        assertNotEquals(one, two);
        two = new Context();
        two.put("ONE", "1");
        two.put("TWO", "2");
        assertEquals(one, two);
        one = new Context();
        two = new Context();
        one.halt();
        assertNotEquals(one, two);
        two.merge(one);
        assertEquals(one, two);
        one.remove("REMOVE_ME");
        assertNotEquals(one, two);
        two.merge(one);
        assertEquals(one, two);
        one = new Context();
        one.remove("REMOVE_ME");
        two = new Context();
        two.remove("REMOVE_ME");
        assertEquals(one, two);
    }
}
