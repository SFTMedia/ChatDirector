package com.blalp.chatdirector.core.utils;

import java.util.HashSet;
import java.util.Set;

import com.blalp.chatdirector.core.model.IDaemon;
import com.blalp.chatdirector.core.model.IItem;

public abstract class ItemDaemon implements IDaemon {
    Set<IItem> items = new HashSet<>();

    public void addItem(IItem item) {
        items.add(item);
    }

    public boolean load() {
        return true;
    }

    public boolean unload() {
        items = new HashSet<>();
        return true;
    }

    public Set<IItem> getItems() {
        return items;
    }
}
