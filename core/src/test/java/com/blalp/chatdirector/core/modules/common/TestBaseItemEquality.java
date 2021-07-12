package com.blalp.chatdirector.core.modules.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBaseItemEquality {

    @Test
    public void testHaltItem() {
        assertEquals(new HaltItem(), new HaltItem());
    }

    @Test
    public void testBreakItem() {
        assertEquals(new BreakItem(), new BreakItem());
    }
}
