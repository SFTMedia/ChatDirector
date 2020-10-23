package com.blalp.chatdirector.modules.conditional;

import java.util.Map;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;

public class IfRegexMatchesItem extends Item {
    IItem nextTrue;
    IItem nextFalse;
    String matches="";
    @Override
    public String process(String string, Map<String,String> context) {
        return string;
    }

    @Override
    public void work(String string,Map<String,String> context) {
        if(string.matches(matches)){
            nextTrue.work(string,context);
        } else {
            nextFalse.work(string,context);
        }
    }
    
}