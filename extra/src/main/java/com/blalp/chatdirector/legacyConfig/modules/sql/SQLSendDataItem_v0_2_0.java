package com.blalp.chatdirector.legacyConfig.modules.sql;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SQLSendDataItem_v0_2_0 implements ILegacyItem {
    String value;
    private boolean attemptedReload = false;
    String table, name, key, connection;
    boolean cache;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }
    @Override
    public Version nextUpdateVersion() {
        return null;
    }
    @Override
    public String name() {
        return null;
    }

    
}
