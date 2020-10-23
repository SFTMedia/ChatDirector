package com.blalp.chatdirector.modules.conditional;

import com.blalp.chatdirector.model.IItem;

public class IfContainsItem implements IItem {
    IItem nextTrue;
    IItem nextFalse;
    String contains="";
    @Override
    public String process(String string) {
        return string;
    }

    @Override
    public void work(String string) {
        if(string.contains(contains)){
            nextTrue.work(string);
        } else {
            nextFalse.work(string);
        }
    }
    
}