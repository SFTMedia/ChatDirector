package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ResolveColorsItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().replaceAll("&([a-z]|[0-9])", "§$1"));
    }

    @Override
    public boolean isValid() {
        return true;
    }

}