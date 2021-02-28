package com.blalp.chatdirector.utils;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

public abstract class ItemDaemon implements IDaemon {
    List<IItem> items = new ArrayList<>();

    public void addItem(IItem item) {
        items.add(item);
    }

    public boolean load() {
        return true;
    }

    public boolean unload() {
        items = new ArrayList<>();
        return true;
    }

    public List<IItem> getItems() {
        return items;
    }
}
