package com.blalp.chatdirector.modules.replacement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.Item;

public class RegexItem extends Item {
    Map<String, String> pairs;

    public RegexItem(Map<String,String> pairs){
        this.pairs=pairs;
    }

    @Override
    public String process(String string) {
        String output = string;
        for (Entry<String, String> map : pairs.entrySet()) {
            output = output.replaceAll(map.getKey(), map.getValue());
        }
        return output;
    }

}