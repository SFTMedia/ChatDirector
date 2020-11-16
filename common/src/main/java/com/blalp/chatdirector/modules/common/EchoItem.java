package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class EchoItem implements IItem {
    public String format;

    public EchoItem(String format) {
        this.format = format;
    }

    @Override
    public boolean isValid() {
        return format!=null;
    }

    @Override
    public Context process(Context context) {
        return new Context(ChatDirector.format(format, context));
    }
    
}
