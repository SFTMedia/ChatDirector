package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ToLowerItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().toLowerCase());
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
}
