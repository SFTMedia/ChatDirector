package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ContextResolveItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(ChatDirector.format(context.getCurrent(), context));
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
}
