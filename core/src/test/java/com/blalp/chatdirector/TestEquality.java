package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.common.BreakItem;
import com.blalp.chatdirector.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestEquality {
    @Test
    public void testContext(){
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
    public void testHaltItem(){
        assertEquals(new HaltItem(), new HaltItem());
    }
    @Test
    public void testBreakItem(){
        assertEquals(new BreakItem(), new BreakItem());
    }
    @Test
    public void testChain(){
        Chain one = new Chain();
        Chain two = new Chain();
        assertEquals(one, two);
        one.addItem(new HaltItem());
        assertNotEquals(one, two);
        two.addItem(new HaltItem());
        assertEquals(one, two);
        one.addItem(new BreakItem());
        assertNotEquals(one, two);
        two.addItem(new BreakItem());
        assertEquals(one, two);
    }
}
