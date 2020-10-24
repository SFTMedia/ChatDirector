package com.blalp.chatdirector.model;

import java.util.Map;

public abstract class Item implements IItem, Runnable {
    public IItem next;
    private String string;
    public Map<String,String> context;
    public abstract String process(String string,Map<String,String> context);
    public void startWork(String string, boolean newThread, Map<String,String> context) {
        this.string=string;
        this.context=context;
        if (newThread){
            new Thread(this);
        }
        run();
    }
    public void run() {
        work(string,context);
    }
    public void work(String string,Map<String,String> context){
        this.context=context;
        string = this.process(string,this.context);
        if(string.equals("")){
            return;
        }
        next.work(string,this.context);
    }
}