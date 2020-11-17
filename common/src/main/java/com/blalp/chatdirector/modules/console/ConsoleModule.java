package com.blalp.chatdirector.modules.console;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

public class ConsoleModule implements IModule {

    @Override
    public void load() {
        // Nothing needed
    }

    @Override
    public void unload() {
        // Nothing needed
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList( "console-output-error", "console-output");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "console-output-error":
                return ConsoleOutputErrorItem.class;
            case "console-output":
                return ConsoleOutputItem.class;
            default:
                return null;
        }
    }
}
