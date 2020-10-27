package com.blalp.chatdirector.modules.format;

import java.util.Map;
import java.util.Map.Entry;
public abstract class Formatter implements IFormatter {

    @Override
    public String format(String format, Map<String, String> context) {
        if(format==null){
            return null;
        }
        for (Entry<String,String> singleContext : context.entrySet()) {
            format=format.replace("%"+singleContext.getKey()+"%", singleContext.getValue());
        }
        return format;
    }
}
