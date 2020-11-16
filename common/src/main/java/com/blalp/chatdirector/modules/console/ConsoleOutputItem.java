package com.blalp.chatdirector.modules.console;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ConsoleOutputItem implements IItem {

    @Override
    public Context process(Context context) {
        System.out.println(ChatDirector.format(context.getCurrent(), context));
        return new Context();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}