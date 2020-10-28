package com.blalp.chatdirector.modules.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class LogicModule extends Module {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public String[] getItemNames() {
        return new String[] { "if-contains", "if-equals", "if-regex-match","split" };
    }

    @Override
    public IItem createItem(String type, Object config) {
        Map<String, Object> configMap;
        ConditionalItem output;
        switch (type) {
            case "if-contains":
                configMap= (Map<String, Object>) config;
                output = new IfContainsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no")),(String)configMap.get("contains"));
                if(configMap.containsKey("stop-on-false")){
                    output.stopOnFalse= (boolean) configMap.get("stop-on-false");
                }
                if(configMap.containsKey("source")){
                    output.source= (String) configMap.get("source");
                }
                if(configMap.containsKey("invert")){
                    output.invert= (boolean) configMap.get("invert");
                }
                return output;
            case "if-equals":
                configMap= (Map<String, Object>) config;
                output = new IfEqualsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no")),(String)configMap.get("equals"));
                if(configMap.containsKey("stop-on-false")){
                    output.stopOnFalse= (boolean) configMap.get("stop-on-false");
                }
                if(configMap.containsKey("source")){
                    output.source= (String) configMap.get("source");
                }
                if(configMap.containsKey("invert")){
                    output.invert= (boolean) configMap.get("invert");
                }
                if(configMap.containsKey("ignore-case")){
                    ((IfEqualsItem)output).ignoreCase= (boolean) configMap.get("ignore-case");
                }
                return output;
            case "if-regex-match":
                configMap= (Map<String, Object>) config;
                output = new IfRegexMatchesItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("yes")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("no")),(String)configMap.get("regex"));
                if(configMap.containsKey("stop-on-false")){
                    output.stopOnFalse= (boolean) configMap.get("stop-on-false");
                }
                if(configMap.containsKey("source")){
                    output.source= (String) configMap.get("source");
                }
                if(configMap.containsKey("invert")){
                    output.invert= (boolean) configMap.get("invert");
                }
                return output;
            case "split":
                ArrayList<IItem> items = new ArrayList<>();
                for (Object item: (ArrayList<?>)config) {
                    items.add(Configuration.loadItems((ArrayList<LinkedHashMap<String,Object>>)((LinkedHashMap<String,?>)item).values().toArray()[0]));
                }
                return new SplitItem(items);
            default:
                return null;
        }
    }
    
}
