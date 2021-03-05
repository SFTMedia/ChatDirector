package com.blalp.chatdirector.extra.modules.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestSQL {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/sql/config.yml").getFile()));
        assertTrue(chatDirector.load());
    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseSend() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sql-send-data"));
        assertEquals(2, chatDirector.getChains().get("sql-send-data").getItems().size());
        SQLSendDataItem sqlSendDataItem = new SQLSendDataItem();
        sqlSendDataItem.connection = "all";
        sqlSendDataItem.table = "Table";
        sqlSendDataItem.name = "Name";
        sqlSendDataItem.key = "Key";
        sqlSendDataItem.value = "Value";
        sqlSendDataItem.cache = true;
        assertEquals(sqlSendDataItem, chatDirector.getChains().get("sql-send-data").getItems().get(0));
        sqlSendDataItem = new SQLSendDataItem();
        sqlSendDataItem.connection = "other";
        sqlSendDataItem.table = "table";
        sqlSendDataItem.name = "name";
        sqlSendDataItem.key = "key";
        sqlSendDataItem.value = "value";
        sqlSendDataItem.cache = false;
        assertEquals(sqlSendDataItem, chatDirector.getChains().get("sql-send-data").getItems().get(1));
    }

    @Test
    public void parseRetrieve() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sql-retrieve-data"));
        assertEquals(2, chatDirector.getChains().get("sql-retrieve-data").getItems().size());
        SQLRetrieveDataItem sqlRetrieveDataItem = new SQLRetrieveDataItem();
        sqlRetrieveDataItem.connection = "all";
        sqlRetrieveDataItem.table = "Table";
        sqlRetrieveDataItem.name = "Name";
        sqlRetrieveDataItem.key = "Key";
        sqlRetrieveDataItem.cache = true;
        assertEquals(sqlRetrieveDataItem, chatDirector.getChains().get("sql-retrieve-data").getItems().get(0));
        sqlRetrieveDataItem = new SQLRetrieveDataItem();
        sqlRetrieveDataItem.connection = "other";
        sqlRetrieveDataItem.table = "table";
        sqlRetrieveDataItem.name = "name";
        sqlRetrieveDataItem.key = "key";
        sqlRetrieveDataItem.cache = false;
        assertEquals(sqlRetrieveDataItem, chatDirector.getChains().get("sql-retrieve-data").getItems().get(1));
    }

    @Test
    public void parseCacheRemove() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sql-cache-remove"));
        assertEquals(2, chatDirector.getChains().get("sql-cache-remove").getItems().size());
        SQLCacheRemoveItem sqlSendDataItem = new SQLCacheRemoveItem();
        sqlSendDataItem.connection = "all";
        sqlSendDataItem.table = "Table";
        sqlSendDataItem.name = "Name";
        sqlSendDataItem.key = "Key";
        assertEquals(sqlSendDataItem, chatDirector.getChains().get("sql-cache-remove").getItems().get(0));
        sqlSendDataItem = new SQLCacheRemoveItem();
        sqlSendDataItem.connection = "other";
        sqlSendDataItem.table = "table";
        sqlSendDataItem.name = "name";
        sqlSendDataItem.key = "key";
        assertEquals(sqlSendDataItem, chatDirector.getChains().get("sql-cache-remove").getItems().get(1));
    }

    @Test
    public void parseCacheIf() {
        init();
        assertTrue(chatDirector.getChains().containsKey("sql-cache-if"));
        assertEquals(2, chatDirector.getChains().get("sql-cache-if").getItems().size());
        SQLCacheIfItem sqlCacheIfItem = new SQLCacheIfItem();
        sqlCacheIfItem.connection = "all";
        sqlCacheIfItem.table = "Table";
        sqlCacheIfItem.name = "Name";
        sqlCacheIfItem.key = "Key";
        Chain chain = new Chain();
        chain.addItem(new HaltItem());
        sqlCacheIfItem.setNoChain(chain);
        assertEquals(sqlCacheIfItem, chatDirector.getChains().get("sql-cache-if").getItems().get(0));
        sqlCacheIfItem = new SQLCacheIfItem();
        sqlCacheIfItem.connection = "other";
        sqlCacheIfItem.table = "table";
        sqlCacheIfItem.name = "name";
        sqlCacheIfItem.key = "key";
        sqlCacheIfItem.setYesChain(chain);
        assertEquals(sqlCacheIfItem, chatDirector.getChains().get("sql-cache-if").getItems().get(1));
    }
}
