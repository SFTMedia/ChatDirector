package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public abstract class PassItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context();
    }
}
