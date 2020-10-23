package com.blalp.chatdirector.internalModules.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formatters extends Formatter {
    public List<IFormatter> formatters = new ArrayList<>();
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> output = new HashMap<>();
        for (IFormatter formatter : formatters) {
            output.putAll(formatter.getContext(event));
        }
        return output;
    }
}
