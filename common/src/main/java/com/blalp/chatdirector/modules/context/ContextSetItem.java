package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class ContextSetItem extends ContextItem {
    String value="%CURRENT%";
    public ContextSetItem(String context) {
        super(context);
    }
    @Override
    public Context process(Context context) {
        Context output = new Context();
        output.put(ChatDirector.format(this.contextName, context), ChatDirector.format(value, context));
        return output;
    }

    @Override
    public boolean isValid() {
        if(value==null){
            return false;
        }
        return super.isValid();
    }
}
