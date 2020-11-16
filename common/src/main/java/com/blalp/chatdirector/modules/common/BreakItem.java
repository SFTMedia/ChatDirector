package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class BreakItem implements IItem {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context process(Context context) {
        return null;
    }
    
}
