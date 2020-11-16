package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class ContextGetItem extends ContextItem {

    public ContextGetItem(String context) {
        super(context);
    }

    @Override
    public Context process(Context context) {
        return new Context(context.get(ChatDirector.format(this.contextName, context)));
    }
    
}
