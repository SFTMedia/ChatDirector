package com.blalp.chatdirector.modules.conditional;

import com.blalp.chatdirector.model.IItem;

public class IfRegexMatchesItem implements IItem {
    IItem nextTrue;
    IItem nextFalse;
    String matches="";
    @Override
    public String process(String string) {
        return string;
    }

    @Override
    public void work(String string) {
        if(string.matches(matches)){
            nextTrue.work(string);
        } else {
            nextFalse.work(string);
        }
    }
    
}