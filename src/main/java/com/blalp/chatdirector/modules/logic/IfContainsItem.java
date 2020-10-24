package com.blalp.chatdirector.modules.logic;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;

public class IfContainsItem extends ConditionalItem {
    String contains;
    public IfContainsItem(IItem nestedTrue,IItem nestedFalse,String contains){
        super(nestedTrue,nestedFalse);
        this.contains=contains;
    }

    @Override
    public boolean test(String string, Map<String, String> context) {
        return ChatDirector.formatter.format(string, context).contains(contains);
    }
}