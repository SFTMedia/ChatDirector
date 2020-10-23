package com.blalp.chatdirector.model;

public abstract class Item implements IItem {
    public Item next;
    public abstract String process(String string);
    public void work(String string){
        string = this.process(string);
        if(string.equals("")){
            return;
        }
        next.work(string);
    }
}