package com.blalp.chatdirector.common.modules.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IModule;
import com.blalp.chatdirector.modules.common.EchoItem;
import com.blalp.chatdirector.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestLogic {

    static ChatDirector chatDirector;

    private void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/logic/config.yml").getFile()));
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
    public void parseIfContains() {
        init();
        Chain targetChain = chatDirector.getChains().get("if-contains");
        assertTrue(chatDirector.getChains().containsKey("if-contains"));
        assertEquals(3, targetChain.getItems().size());
        IfContainsItem ifContainsItem = new IfContainsItem();
        Chain chain = new Chain();
        chain.getItems().add(new HaltItem());
        ifContainsItem.yesChain = chain;
        ifContainsItem.noChain = chain;
        ifContainsItem.contains="String";
        ifContainsItem.source="Source";
        assertEquals(ifContainsItem, targetChain.getItems().get(0));
        ifContainsItem = new IfContainsItem();
        chain = new Chain();
        chain.getItems().add(new HaltItem());
        ifContainsItem.yesChain = chain;
        ifContainsItem.contains="String";
        assertEquals(ifContainsItem, targetChain.getItems().get(1));
        ifContainsItem = new IfContainsItem();
        chain = new Chain();
        ifContainsItem.contains="String";
        chain.addItem(new HaltItem());
        ifContainsItem.noChain=chain;
        chain=new Chain();
        chain.getItems().add(ifContainsItem);
        ifContainsItem= new IfContainsItem();
        ifContainsItem.yesChain = chain;
        ifContainsItem.contains="String";
        assertEquals(ifContainsItem, targetChain.getItems().get(2));
    }

    @Test
    public void parseIfEquals() {
        init();
        Chain targetChain = chatDirector.getChains().get("if-equals");
        assertTrue(chatDirector.getChains().containsKey("if-equals"));
        assertEquals(2, targetChain.getItems().size());
        IfEqualsItem ifEqualsItem = new IfEqualsItem();
        Chain chain = new Chain();
        ifEqualsItem.equals="test";
        chain.getItems().add(new HaltItem());
        ifEqualsItem.noChain=chain;
        chain=new Chain();
        chain.addItem(ifEqualsItem);
        ifEqualsItem=new IfEqualsItem();
        ifEqualsItem.yesChain=chain;
        chain=new Chain();
        chain.getItems().add(new HaltItem());
        ifEqualsItem.noChain = chain;
        ifEqualsItem.equals="String";
        ifEqualsItem.source="%CURRENT%";
        ifEqualsItem.ignoreCase=false;
        assertEquals(ifEqualsItem, targetChain.getItems().get(0));
        ifEqualsItem = new IfEqualsItem();
        chain = new Chain();
        chain.getItems().add(new HaltItem());
        ifEqualsItem.noChain = chain;
        ifEqualsItem.equals="String";
        assertEquals(ifEqualsItem, targetChain.getItems().get(1));
    }

    @Test
    public void parseIfRegexMatch() {
        init();
        Chain targetChain = chatDirector.getChains().get("if-regex-match");
        assertTrue(chatDirector.getChains().containsKey("if-regex-match"));
        assertEquals(2, targetChain.getItems().size());
        IfRegexMatchesItem ifRegexEqualsItem = new IfRegexMatchesItem();
        Chain chain = new Chain();
        ifRegexEqualsItem.match="[sS]tring";
        ifRegexEqualsItem.source="%CURRENT%";
        chain.addItem(new EchoItem("yes"));
        ifRegexEqualsItem.yesChain=chain;
        chain=new Chain();
        chain.addItem(new EchoItem("no"));
        ifRegexEqualsItem.noChain=chain;
        assertEquals(ifRegexEqualsItem, targetChain.getItems().get(0));
        ifRegexEqualsItem = new IfRegexMatchesItem();
        chain = new Chain();
        chain.getItems().add(new HaltItem());
        ifRegexEqualsItem.yesChain = chain;
        ifRegexEqualsItem.match="[sS]tring";
        assertEquals(ifRegexEqualsItem, targetChain.getItems().get(1));
    }

    @Test
    public void parseSplit() {
        init();
        Chain targetChain = chatDirector.getChains().get("split");
        assertTrue(chatDirector.getChains().containsKey("split"));
        assertEquals(2, targetChain.getItems().size());
        SplitItem splitItem = new SplitItem();
        Chain chain = new Chain();
        chain.getItems().add(new EchoItem("1"));
        splitItem.chains.add(chain);
        chain= new Chain();
        chain.getItems().add(new EchoItem("2"));
        splitItem.chains.add(chain);
        assertEquals(splitItem, targetChain.getItems().get(0));
        splitItem = new SplitItem();
        assertEquals(splitItem, targetChain.getItems().get(1));
    }

    @Test
    public void parseStartsWith() {
        init();
        Chain targetChain = chatDirector.getChains().get("if-starts-with");
        assertTrue(chatDirector.getChains().containsKey("if-starts-with"));
        assertEquals(2, targetChain.getItems().size());
        IfStartsWithItem startsWithItem = new IfStartsWithItem();
        Chain chain = new Chain();
        chain.getItems().add(new EchoItem("yes"));
        startsWithItem.yesChain=chain;
        chain= new Chain();
        chain.getItems().add(new EchoItem("no"));
        startsWithItem.noChain=chain;
        startsWithItem.starts="String";
        startsWithItem.source="%CURRENT%";
        assertEquals(startsWithItem, targetChain.getItems().get(0));
        startsWithItem = new IfStartsWithItem();
        chain = new Chain();
        chain.getItems().add(new HaltItem());
        startsWithItem.yesChain=chain;
        startsWithItem.starts="2";
        assertEquals(startsWithItem, targetChain.getItems().get(1));
    }

    @Test
    public void parseEndsWith() {
        init();
        Chain targetChain = chatDirector.getChains().get("if-ends-with");
        assertTrue(chatDirector.getChains().containsKey("if-ends-with"));
        assertEquals(2, targetChain.getItems().size());
        IfEndsWithItem startsWithItem = new IfEndsWithItem();
        Chain chain = new Chain();
        chain.getItems().add(new EchoItem("yes"));
        startsWithItem.yesChain=chain;
        chain= new Chain();
        chain.getItems().add(new EchoItem("no"));
        startsWithItem.noChain=chain;
        startsWithItem.ends="String";
        startsWithItem.source="%CURRENT%";
        assertEquals(startsWithItem, targetChain.getItems().get(0));
        startsWithItem = new IfEndsWithItem();
        chain = new Chain();
        chain.getItems().add(new HaltItem());
        startsWithItem.yesChain=chain;
        startsWithItem.ends="2";
        assertEquals(startsWithItem, targetChain.getItems().get(1));
    }
}
