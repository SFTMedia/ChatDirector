package com.blalp.chatdirector.modules.logic;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;

public class IfStartsWithItem extends ConditionalItem {
    String startsWith;
    public IfStartsWithItem (IItem nestedTrue,IItem nestedFalse, String startsWith,String source) {
        super(nestedTrue, nestedFalse);
        this.startsWith=startsWith;
        this.source=source;
    }
    @Override
    public boolean test(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        String str =  ChatDirector.format(source, context);
        return str.endsWith(startsWith);
    }
}
