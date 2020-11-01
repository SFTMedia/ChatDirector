package com.blalp.chatdirector.modules.context;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

public class ContextResolveItem extends Item {

    @Override
    public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
        return ChatDirector.formatter.format(string, context);
    }
    
}
