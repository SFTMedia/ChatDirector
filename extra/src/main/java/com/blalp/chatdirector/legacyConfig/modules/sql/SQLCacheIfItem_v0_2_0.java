package com.blalp.chatdirector.legacyConfig.modules.sql;

import java.util.List;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SQLCacheIfItem_v0_2_0 implements ILegacyItem {
    String table, name, key, connection;
    boolean cache;
    LegacyChain yesChain;
    LegacyChain noChain;
    String source = "%CURRENT%";

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
        return "sql-cache-if";
    }

}
