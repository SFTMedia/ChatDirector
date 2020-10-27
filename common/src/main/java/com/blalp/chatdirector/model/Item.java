package com.blalp.chatdirector.model;

import java.util.Map;

import com.blalp.chatdirector.configuration.Configuration;

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
        if(Configuration.debug){
            System.out.println("Starting work of "+getClass().getCanonicalName()+"("+this+") with string >"+string+"<");
            System.out.println(context);
        }
        this.context=context;
        string = this.process(string,this.context);
        if(Configuration.debug){
            System.out.println("Processed to "+getClass().getCanonicalName()+"("+this+") with string >"+string+"<");
            System.out.println(context);
        }
        if(string.equals("")){
            return;
        }
        if(next!=null){
            next.work(string,this.context);
        } else {
            if(Configuration.debug){
                System.out.println("Next was null, aborting with string >"+string+"<");
            }
        }
    }
}