package com.blalp.chatdirector.modules.console;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ConsoleModule extends Module {

    @Override
    public void load() {
        // Nothing needed
    }

    @Override
    public void unload() {
        // Nothing needed
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"ConsoleOutputError","ConsoleOutput"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "ConsoleOutputError":
                return new ConsoleOutputErrorItem();
            case "ConsoleOutput":
                return new ConsoleOutputItem();
        }
        return null;
    }
}
