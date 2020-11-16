package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ToUpperItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().toUpperCase());
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
}
