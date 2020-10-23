package com.blalp.chatdirector.modules.conditional;

import com.blalp.chatdirector.model.IItem;

public class IfEqualsItem implements IItem {
    IItem nextTrue;
    IItem nextFalse;
    String equals="";
    boolean ignoreCase=false;
    @Override
    public String process(String string) {
        return string;
    }

    @Override
    public void work(String string) {
        if(string.equals(equals)||(ignoreCase&&string.equalsIgnoreCase(equals))){
            nextTrue.work(string);
        } else {
            nextFalse.work(string);
        }
    }
    
}