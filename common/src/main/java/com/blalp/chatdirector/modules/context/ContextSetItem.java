package com.blalp.chatdirector.modules.context;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class ContextSetItem extends ContextItem {
    String value="%CURRENT%";
    public ContextSetItem(String context) {
        super(context);
    }
    @Override
    public String process(String string, Map<String, String> context) {
        this.context.put("CURRENT", string);
        this.context.put(ChatDirector.format(this.contextName, context), ChatDirector.format(value, context));
        return string;
    }
    
}
