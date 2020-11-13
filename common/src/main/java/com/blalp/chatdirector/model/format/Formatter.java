package com.blalp.chatdirector.model.format;

import java.util.Map;
import java.util.Map.Entry;
public abstract class Formatter implements IFormatter {

    @Override
    public String format(String format, Map<String, String> context) {
        if(format==null){
            return null;
        }
        synchronized (context) {
            for (Entry<String,String> singleContext : context.entrySet()) {
                if(singleContext.getKey()!=null&singleContext.getValue()!=null){
                    format=format.replace("%"+singleContext.getKey()+"%", singleContext.getValue());
                }
            }
        }
        return format;
    }
}
