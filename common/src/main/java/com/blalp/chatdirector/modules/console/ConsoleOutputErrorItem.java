package com.blalp.chatdirector.modules.console;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

public class ConsoleOutputErrorItem extends Item {

    @Override
    public String process(String string, Map<String,String> context) {
        System.err.println(ChatDirector.formatter.format(string, context));
        return string;
    }
}