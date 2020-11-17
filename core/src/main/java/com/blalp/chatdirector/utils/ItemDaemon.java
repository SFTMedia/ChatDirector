package com.blalp.chatdirector.utils;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Loadable;

public abstract class ItemDaemon extends Loadable {
    public List<IItem> items = new ArrayList<>();

    public void addItem(IItem item) {
        items.add(item);
    }

    public void load() {
    }

    public void unload() {
        items = new ArrayList<>();
    }
}
