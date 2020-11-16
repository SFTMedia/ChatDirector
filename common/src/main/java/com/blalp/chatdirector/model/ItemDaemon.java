package com.blalp.chatdirector.model;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.ChatDirector;

public abstract class ItemDaemon extends Loadable {
    public List<IItem> items = new ArrayList<>();
    public void addItem(IItem item,Chain chain){
        items.add(item);
        ChatDirector.addListenerItem(item, chain);
    }
    public void load(){}
    public void unload(){
        items=new ArrayList<>();
    }
}
