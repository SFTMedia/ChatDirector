package com.blalp.chatdirector.modules.replacement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.Item;

public class RegexItem extends Item {
    Map<String, String> pairs = new LinkedHashMap<String,String>();

    public RegexItem(ArrayList<Map<String,String>> pairsList){
        for(Map<String,String> pair : pairsList){
            pairs.putAll(pair);
        }
    }

    @Override
    public String process(String string, Map<String,String> context) {
        String output = string;
        for (Entry<String, String> map : pairs.entrySet()) {
            output = output.replaceAll(map.getKey(), map.getValue());
        }
        return output;
    }

}