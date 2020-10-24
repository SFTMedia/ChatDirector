package com.blalp.chatdirector.modules.logic;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;

public class IfEqualsItem extends ConditionalItem {
    String equals;
    boolean ignoreCase=false;
    public IfEqualsItem(IItem nestedTrue,IItem nestedFalse,String equals) {
        super(nestedTrue, nestedFalse);
        this.equals=equals;
    }

    @Override
    public boolean test(String string, Map<String, String> context) {
        String str =  ChatDirector.formatter.format(string, context);
        return (str.equals(equals)||(ignoreCase&&str.equalsIgnoreCase(equals)));
    }
    
}