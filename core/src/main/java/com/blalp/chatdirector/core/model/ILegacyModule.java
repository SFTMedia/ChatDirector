package com.blalp.chatdirector.core.model;

import java.util.List;

public interface ILegacyModule {
    public List<String> getItemNames(Version version);

    public Class<? extends ILegacyItem> getItemClass(String type, Version version);

    public String getItemName(Class<? extends ILegacyItem> itemClass);
}