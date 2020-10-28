package com.blalp.chatdirector.modules.context;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class ContextRemoveItem extends ContextItem {

    public ContextRemoveItem(String context) {
        super(context);
    }
    @Override
    public String process(String string, Map<String, String> context) {
        this.context.remove(ChatDirector.formatter.format(this.contextName, context));
        return string;
    }
    
}
