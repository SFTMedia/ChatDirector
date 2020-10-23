package com.blalp.chatdirector.internalModules.common;

import com.blalp.chatdirector.model.IItem;

public class NullItem implements IItem {

    @Override
    public String process(String string) {
        return null;
    }

    @Override
    public void work(String string) {
        return;
    }
    
}
