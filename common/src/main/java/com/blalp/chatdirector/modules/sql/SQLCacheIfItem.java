package com.blalp.chatdirector.modules.sql;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.logic.ConditionalItem;

public class SQLCacheIfItem extends ConditionalItem {
    public String table,name,key,connectionName;
    public boolean cache;
    public SQLCacheIfItem(IItem nestedTrue, IItem nestedFalse,String table,String name,String key,String connectionName,boolean cache){
        super(nestedTrue, nestedFalse);
        this.key=key;
        this.table=table;
        this.name=name;
        this.key=key;
        this.connectionName=connectionName;
        this.cache=cache;
    }
    @Override
    public boolean test(String string, Map<String, String> context) {
        return SQLCacheStore.containsKey(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context));
    }
    
}
