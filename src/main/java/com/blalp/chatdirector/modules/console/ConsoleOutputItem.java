package com.blalp.chatdirector.modules.console;

import com.blalp.chatdirector.model.Item;

public class ConsoleOutputItem extends Item {

    @Override
    public String process(String string) {
        System.out.println(string);
        return string;
    }
}