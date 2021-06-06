package com.blalp.chatdirector.legacyConfig.modules.sql;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SQLLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("send-data", "retrieve-data", "sql-cache-if", "sql-cache-remove");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "send-data":
            return SQLSendDataItem_v0_2_0.class;
        case "retrieve-data":
            return SQLRetrieveDataItem_v0_2_0.class;
        case "sql-cache-if":
            return SQLCacheIfItem_v0_2_0.class;
        case "sql-cache-remove":
            return SQLCacheRemoveItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
