package com.blalp.chatdirector.modules.replacement;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class SubStringItem extends Item {
    public int start=0;
    public int end=-1;
    @Override
    public String process(String string, Map<String, String> context) {
        if(end==-1){
            return string.substring(start);
        } else {
            return string.substring(start,end);
        }
    }
    
}
