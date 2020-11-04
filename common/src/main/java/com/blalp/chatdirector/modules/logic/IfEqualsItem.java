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
        context.put("CURRENT", string);
        String str =  ChatDirector.format(source, context);
        return (str.equals(ChatDirector.format(equals,context))||(ignoreCase&&str.equalsIgnoreCase(ChatDirector.format(equals,context))));
    }
    
}