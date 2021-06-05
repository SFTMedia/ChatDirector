package com.blalp.chatdirector.core.model;

import java.util.List;

public interface ILegacyItem {
    /**
     * 
     * @return (can be null) An array of functionally identical items
     */
    public List<ILegacyItem> updateToNextLegacyItems();
    public Version nextUpdateVersion();
}
