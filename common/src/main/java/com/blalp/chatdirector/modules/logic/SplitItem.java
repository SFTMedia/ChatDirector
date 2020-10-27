package com.blalp.chatdirector.modules.logic;

import java.util.ArrayList;
import java.util.Map;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.model.IItem;

public class SplitItem extends PassItem {
    ArrayList<IItem> items = new ArrayList<IItem>();
    public SplitItem(ArrayList<IItem> items) {
        this.items=items;
    }
    @Override
    public void work(String string, Map<String, String> context) {
        if(string.isEmpty()){
            return;
        }
        for (IItem item : items) {
            item.startWork(string, true, context);
        }
    }
}