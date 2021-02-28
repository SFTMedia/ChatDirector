package com.blalp.chatdirector.common.modules.replacement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;

import org.junit.jupiter.api.Test;

public class TestReplacement {

    static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/replacement/config.yml").getFile()));
        chatDirector.load();

    }

    @Test
    public void valid() {
        init();
        for (IModule module : ChatDirector.getConfig().getModules()) {
            assertTrue(module.isValid());
        }
        for (Chain chain : ChatDirector.getConfig().getChains().values()) {
            assertTrue(chain.isValid());
        }
    }

    @Test
    public void parseRegex() {
        init();
        Chain targetChain = chatDirector.getChains().get("regex");
        assertTrue(chatDirector.getChains().containsKey("regex"));
        assertEquals(1, targetChain.items.size());
        RegexItem regexItem = new RegexItem();
        regexItem.pairs.put("Regex to find", "regex to replace");
        regexItem.pairs.put("Regex to find2", "regex to replace2");
        assertEquals(regexItem, targetChain.items.get(0));
    }

    @Test
    public void parseRemoveColors() {
        init();
        Chain targetChain = chatDirector.getChains().get("remove-colors");
        assertTrue(chatDirector.getChains().containsKey("remove-colors"));
        assertEquals(2, targetChain.items.size());
        assertEquals(new RemoveColorsItem(), targetChain.items.get(0));
        assertEquals(new RemoveColorsItem(), targetChain.items.get(1));
    }

    @Test
    public void parseResolveColors() {
        init();
        Chain targetChain = chatDirector.getChains().get("resolve-colors");
        assertTrue(chatDirector.getChains().containsKey("resolve-colors"));
        assertEquals(2, targetChain.items.size());
        assertEquals(new ResolveColorsItem(), targetChain.items.get(0));
        assertEquals(new ResolveColorsItem(), targetChain.items.get(1));
    }

    @Test
    public void parseToUpper() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-upper");
        assertTrue(chatDirector.getChains().containsKey("to-upper"));
        assertEquals(2, targetChain.items.size());
        assertEquals(new ToUpperItem(), targetChain.items.get(0));
        assertEquals(new ToUpperItem(), targetChain.items.get(1));
    }

    @Test
    public void parseToLower() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-lower");
        assertTrue(chatDirector.getChains().containsKey("to-lower"));
        assertEquals(2, targetChain.items.size());
        assertEquals(new ToLowerItem(), targetChain.items.get(0));
        assertEquals(new ToLowerItem(), targetChain.items.get(1));
    }

    @Test
    public void parseToWord() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-word");
        assertTrue(chatDirector.getChains().containsKey("to-word"));
        assertEquals(2, targetChain.items.size());
        assertEquals(new ToWordItem(), targetChain.items.get(0));
        assertEquals(new ToWordItem(), targetChain.items.get(1));
    }

    @Test
    public void parseSubString() {
        init();
        Chain targetChain = chatDirector.getChains().get("sub-string");
        assertTrue(chatDirector.getChains().containsKey("sub-string"));
        assertEquals(2, targetChain.items.size());
        SubStringItem subStringItem = new SubStringItem();
        subStringItem.start=2;
        subStringItem.end=4;
        assertEquals(subStringItem, targetChain.items.get(0));
        subStringItem = new SubStringItem();
        subStringItem.end=4;
        assertEquals(subStringItem, targetChain.items.get(1));
    }
}
