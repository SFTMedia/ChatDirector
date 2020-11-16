package com.blalp.chatdirector.modules.sql;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public abstract class SQLItem implements IItem {
    public String table,name,key,connectionName;
    public boolean cache;
    public SQLItem(String table,String name,String key,String connectionName,boolean cache){
        this.table=table;
        this.name=name;
        this.key=key;
        this.connectionName=connectionName;
        this.cache=cache;
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(table,name,key,connectionName);
    }
}
