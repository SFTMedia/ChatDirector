package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class ContextRemoveItem extends ContextItem {

    public ContextRemoveItem(String context) {
        super(context);
    }
    @Override
    public Context process(Context context) {
        Context output = new Context();
        output.remove(ChatDirector.format(this.contextName, context));
        return output;
    }
    
}
