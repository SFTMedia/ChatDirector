package com.blalp.chatdirector.modules.replacement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IteratorIterable;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class RegexItem implements IItem {
    Map<String, String> pairs = new LinkedHashMap<String,String>();

    public RegexItem(JsonNode config){
        for(Entry<String,JsonNode> pair : new IteratorIterable<>(config.fields())) {
            pairs.put(pair.getKey(),pair.getValue().asText());
        }
    }

    @Override
    public Context process(Context context) {
        String output = context.getCurrent();
        for (Entry<String, String> map : pairs.entrySet()) {
            output = output.replaceAll(map.getKey(), map.getValue());
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(pairs);
    }

}