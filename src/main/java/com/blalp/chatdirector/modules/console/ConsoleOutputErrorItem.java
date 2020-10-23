package com.blalp.chatdirector.modules.console;

import com.blalp.chatdirector.model.Item;

public class ConsoleOutputErrorItem extends Item {

    @Override
    public String process(String string) {
        System.err.println(string);
        return string;
    }
}