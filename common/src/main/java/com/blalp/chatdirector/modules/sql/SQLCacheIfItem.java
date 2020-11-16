package com.blalp.chatdirector.modules.sql;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.logic.ConditionalItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class SQLCacheIfItem extends ConditionalItem {
    public String table,name,key,connectionName;
    public boolean cache;
    public SQLCacheIfItem(Chain nestedTrue, Chain nestedFalse,String table,String name,String key,String connectionName,boolean cache){
        super(nestedTrue, nestedFalse);
        this.key=key;
        this.table=table;
        this.name=name;
        this.key=key;
        this.connectionName=connectionName;
        this.cache=cache;
    }
    @Override
    public boolean test(Context context) {
        return SQLCacheStore.containsKey(connectionName, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context));
    }

    @Override
    public boolean isValid() {
        return  ValidationUtils.hasContent(table,name,key,connectionName)&&super.isValid();
    }
    
}
