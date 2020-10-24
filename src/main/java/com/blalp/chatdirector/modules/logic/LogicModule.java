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
        switch (type) {
            case "if-contains":
                configMap= (Map<String, Object>) config;
                return new IfContainsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("contains"));
            case "if-equals":
                configMap= (Map<String, Object>) config;
                return new IfEqualsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("equals"));
            case "if-regex-match":
                configMap= (Map<String, Object>) config;
                return new IfRegexMatchesItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("regex"));
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
