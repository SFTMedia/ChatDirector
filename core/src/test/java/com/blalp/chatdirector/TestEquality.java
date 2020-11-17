package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.swing.text.AbstractDocument.Content;

import com.blalp.chatdirector.model.Context;

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
        assertEquals(one.hashCode(), two.hashCode());
        assertEquals(one, two);
        assertEquals(one.getCurrent(), two.getCurrent());
        one.halt();
        assertNotEquals(one, two);
        two.halt();
        assertEquals(one, two);
        one = new Context();
        one.put("CURRENT", "TEST2");
        two = new Context("TEST2");
        assertEquals(one, two);
    }
}
