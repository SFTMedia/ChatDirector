package com.blalp.chatdirector.modules.console;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class ConsoleOutputErrorItem extends Item {

    @Override
    public String process(String string, Map<String,String> context) {
        System.err.println(string);
        return string;
    }
}