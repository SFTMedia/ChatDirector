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

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/replacement/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseRegex() {
        init();
        Chain targetChain = chatDirector.getChains().get("regex");
        assertTrue(chatDirector.getChains().containsKey("regex"));
        assertEquals(1, targetChain.getItems().size());
        RegexItem regexItem = new RegexItem();
        regexItem.pairs.put("Regex to find", "regex to replace");
        regexItem.pairs.put("Regex to find2", "regex to replace2");
        assertEquals(regexItem, targetChain.getItems().get(0));
    }

    @Test
    public void parseRemoveColors() {
        init();
        Chain targetChain = chatDirector.getChains().get("remove-colors");
        assertTrue(chatDirector.getChains().containsKey("remove-colors"));
        assertEquals(2, targetChain.getItems().size());
        assertEquals(new RemoveColorsItem(), targetChain.getItems().get(0));
        assertEquals(new RemoveColorsItem(), targetChain.getItems().get(1));
    }

    @Test
    public void parseResolveColors() {
        init();
        Chain targetChain = chatDirector.getChains().get("resolve-colors");
        assertTrue(chatDirector.getChains().containsKey("resolve-colors"));
        assertEquals(2, targetChain.getItems().size());
        assertEquals(new ResolveColorsItem(), targetChain.getItems().get(0));
        assertEquals(new ResolveColorsItem(), targetChain.getItems().get(1));
    }

    @Test
    public void parseToUpper() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-upper");
        assertTrue(chatDirector.getChains().containsKey("to-upper"));
        assertEquals(2, targetChain.getItems().size());
        assertEquals(new ToUpperItem(), targetChain.getItems().get(0));
        assertEquals(new ToUpperItem(), targetChain.getItems().get(1));
    }

    @Test
    public void parseToLower() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-lower");
        assertTrue(chatDirector.getChains().containsKey("to-lower"));
        assertEquals(2, targetChain.getItems().size());
        assertEquals(new ToLowerItem(), targetChain.getItems().get(0));
        assertEquals(new ToLowerItem(), targetChain.getItems().get(1));
    }

    @Test
    public void parseToWord() {
        init();
        Chain targetChain = chatDirector.getChains().get("to-word");
        assertTrue(chatDirector.getChains().containsKey("to-word"));
        assertEquals(2, targetChain.getItems().size());
        assertEquals(new ToWordItem(), targetChain.getItems().get(0));
        assertEquals(new ToWordItem(), targetChain.getItems().get(1));
    }

    @Test
    public void parseSubString() {
        init();
        Chain targetChain = chatDirector.getChains().get("sub-string");
        assertTrue(chatDirector.getChains().containsKey("sub-string"));
        assertEquals(2, targetChain.getItems().size());
        SubStringItem subStringItem = new SubStringItem();
        subStringItem.start=2;
        subStringItem.end=4;
        assertEquals(subStringItem, targetChain.getItems().get(0));
        subStringItem = new SubStringItem();
        subStringItem.end=4;
        assertEquals(subStringItem, targetChain.getItems().get(1));
    }
}
