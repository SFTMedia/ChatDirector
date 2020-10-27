package com.blalp.chatdirector.modules.replacement;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class RemoveColorsItem extends Item {

    @Override
    public String process(String string, Map<String,String> context) {
        return string.replaceAll("&([a-z]|[0-9])", "");
    }

}