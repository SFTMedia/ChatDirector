package com.blalp.chatdirector.modules.replacement;

import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class ToWordItem extends Item {

    @Override
    public String process(String string, Map<String, String> context) {
        String output = "";
        for(String word: string.split(" ")){
            if(!output.isEmpty()){
                output+=" ";
            }
            output+=word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase();
        }
        return output;
    }
    
}
