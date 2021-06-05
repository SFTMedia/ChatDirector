package com.blalp.chatdirector.core.model;

import java.util.List;

public interface ILegacyModule {
    public List<String> getItemNames(Version version);

    public Class<? extends IItem> getItemClass(String type,Version version);
}