package com.blalp.chatdirector.modules.context;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class ContextGetItem extends ContextItem {

    public ContextGetItem(String context) {
        super(context);
    }
    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        return context.get(ChatDirector.formatter.format(this.contextName, context));
    }
    
}
