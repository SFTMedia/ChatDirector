package com.blalp.chatdirector.extra.modules.luckperms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.modules.common.EchoItem;

import org.junit.jupiter.api.Test;

public class TestLuckPerms {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/luckperms/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseContext() {
        init();
        assertTrue(chatDirector.getChains().containsKey("luckperms-context"));
        assertEquals(1, chatDirector.getChains().get("luckperms-context").getItems().size());
        LuckPermsContextItem luckpermsContextItem = new LuckPermsContextItem();
        assertEquals(luckpermsContextItem, chatDirector.getChains().get("luckperms-context").getItems().get(0));
    }

    @Test
    public void parseHas() {
        init();
        assertTrue(chatDirector.getChains().containsKey("luckperms-has"));
        assertEquals(2, chatDirector.getChains().get("luckperms-has").getItems().size());
        LuckPermsHasItem luckpermsHasItem = new LuckPermsHasItem();
        luckpermsHasItem.permission="permission.node";
        Chain chain = new Chain();
        chain.addItem(new EchoItem("yes"));
        luckpermsHasItem.setYesChain(chain);
        assertEquals(luckpermsHasItem, chatDirector.getChains().get("luckperms-has").getItems().get(0));
        luckpermsHasItem = new LuckPermsHasItem();
        luckpermsHasItem.permission="permission.node 2";
        chain = new Chain();
        chain.addItem(new EchoItem("no"));
        luckpermsHasItem.setNoChain(chain);
        assertEquals(luckpermsHasItem, chatDirector.getChains().get("luckperms-has").getItems().get(1));
    }

    @Test
    public void parseSet() {
        init();
        assertTrue(chatDirector.getChains().containsKey("luckperms-set"));
        assertEquals(2, chatDirector.getChains().get("luckperms-set").getItems().size());
        LuckPermsSetItem luckpermsSetItem = new LuckPermsSetItem();
        luckpermsSetItem.permission="permission.node";
        luckpermsSetItem.value=false;
        assertEquals(luckpermsSetItem, chatDirector.getChains().get("luckperms-set").getItems().get(0));
        luckpermsSetItem = new LuckPermsSetItem();
        luckpermsSetItem.permission="permission.node 2";
        luckpermsSetItem.value=true;
        assertEquals(luckpermsSetItem, chatDirector.getChains().get("luckperms-set").getItems().get(1));
    }

    @Test
    public void parseUnset() {
        init();
        assertTrue(chatDirector.getChains().containsKey("luckperms-unset"));
        assertEquals(2, chatDirector.getChains().get("luckperms-unset").getItems().size());
        LuckPermsUnsetItem luckpermsSetItem = new LuckPermsUnsetItem();
        luckpermsSetItem.permission="permission.node";
        luckpermsSetItem.value=false;
        assertEquals(luckpermsSetItem, chatDirector.getChains().get("luckperms-unset").getItems().get(0));
        luckpermsSetItem = new LuckPermsUnsetItem();
        luckpermsSetItem.permission="permission.node 2";
        luckpermsSetItem.value=true;
        assertEquals(luckpermsSetItem, chatDirector.getChains().get("luckperms-unset").getItems().get(1));
    }
}
