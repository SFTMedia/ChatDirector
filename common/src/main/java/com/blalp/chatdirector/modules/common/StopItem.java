package com.blalp.chatdirector.modules.common;

import java.util.Map;

import com.blalp.chatdirector.model.IItem;

public class StopItem implements IItem {

    @Override
    public String process(String string, Map<String,String> context) {
        return string;
    }

    @Override
    public String work(String string, Map<String,String> context) {
        return string;
    }

    @Override
    public void startWork(String string, boolean newThread, Map<String, String> context) {
        return;
    }
    
}
