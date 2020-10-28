package com.blalp.chatdirector.modules.common;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

public class ReloadItem extends Item {

    @Override
    public String process(String string, Map<String, String> context) {
        ChatDirector.instance.reload();
        return string;
    }
    
}
