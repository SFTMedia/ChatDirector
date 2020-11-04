package com.blalp.chatdirector.modules.common;

import java.util.Map;

import com.blalp.chatdirector.model.IItem;

public class HaltItem implements IItem {

    @Override
    public String process(String string, Map<String,String> context) {
        return "";
    }

    @Override
    public String work(String string, Map<String,String> context) {
        return "";
    }

    @Override
    public void startWork(String string, boolean newThread, Map<String, String> context) {
        return;
    }
    
}
