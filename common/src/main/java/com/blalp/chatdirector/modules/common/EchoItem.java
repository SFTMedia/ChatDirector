package com.blalp.chatdirector.modules.common;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;

public class EchoItem extends Item {
    public String format = "%CURRENT%";

    public EchoItem(String format){
        this.format=format;
    }

    @Override
    public String process(String string, Map<String,String> context) {
        if(context.containsKey("CURRENT")){
            context.put("LAST", context.get("CURRENT"));
        }
        context.put("CURRENT", string);
        return ChatDirector.formatter.format(format, context);
    }
    
}
