package com.blalp.chatdirector.internalModules.format;

import java.util.Map;
import java.util.Map.Entry;

public abstract class Formatter implements IFormatter {

    @Override
    public String format(String format, Map<String, String> context) {
        for (Entry<String,String> singleContext : context.entrySet()) {
            format.replaceAll("%"+singleContext.getKey()+"%", singleContext.getValue());
        }
        return format;
    }
}
