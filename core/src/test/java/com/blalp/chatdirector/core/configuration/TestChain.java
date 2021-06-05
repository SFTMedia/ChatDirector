package com.blalp.chatdirector.core.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.blalp.chatdirector.core.modules.common.BreakItem;
import com.blalp.chatdirector.core.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestChain {

    @Test
    public void testChain() {
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
