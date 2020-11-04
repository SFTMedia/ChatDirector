package com.blalp.chatdirector.model;

import java.util.Map;

public interface IItem {
    public String process(String string, Map<String,String> context); // This does the internal calculations
    public String work(String string, Map<String,String> context); // This controls who the data gets handed off to
    public abstract void startWork(String string, boolean newThread, Map<String,String> context);// This is the start of the work
}