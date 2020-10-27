package com.blalp.chatdirector.modules.common;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class PassItem extends Item {

    @Override
    public String process(String string, Map<String,String> context) {
        return string;
    }
    
}
