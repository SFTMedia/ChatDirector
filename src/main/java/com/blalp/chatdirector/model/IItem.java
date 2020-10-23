package com.blalp.chatdirector.model;

public interface IItem {
    public String process(String string); // This does the internal calculations
    public void work(String string); // This controls who the data gets handed off to
}