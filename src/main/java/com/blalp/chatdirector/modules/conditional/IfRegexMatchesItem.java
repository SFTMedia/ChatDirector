package com.blalp.chatdirector.modules.conditional;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.Item;

public class IfRegexMatchesItem extends ConditionalItem {
    String regex;
    public IfRegexMatchesItem(IItem nestedTrue, IItem nestedFalse,String regex) {
        super(nestedTrue, nestedFalse);
        this.regex=regex;
    }

    @Override
    public boolean test(String string, Map<String, String> context) {
        return (ChatDirector.formatter.format(string, context).matches(regex));
    }
    
}