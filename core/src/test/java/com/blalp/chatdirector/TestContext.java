package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.blalp.chatdirector.model.Context;

import org.junit.jupiter.api.Test;

public class TestContext {
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
        assertEquals(one,two);
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
