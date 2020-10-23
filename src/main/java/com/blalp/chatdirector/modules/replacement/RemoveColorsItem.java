package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Item;

public class RemoveColorsItem extends Item {

    @Override
    public String process(String string) {
        return string.replaceAll("&([a-z]|[0-9])", "");
    }

}