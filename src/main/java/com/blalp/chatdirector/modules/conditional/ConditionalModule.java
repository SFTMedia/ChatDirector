package com.blalp.chatdirector.modules.conditional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ConditionalModule extends Module {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public String[] getItemNames() {
        return new String[] { "if-contains", "if-equals", "if-regex-match" };
    }

    @Override
    public IItem createItem(String type, Object config) {
        Map<String, Object> configMap = (Map<String, Object>) config;
        switch (type) {
            case "if-contains":
                return new IfContainsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("contains"));
            case "if-equals":
                return new IfEqualsItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("equals"));
            case "if-regex-match":
                return new IfRegexMatchesItem(Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("true")),Configuration.loadItems((ArrayList<LinkedHashMap<String, Object>>) configMap.get("false")),(String)configMap.get("regex"));
        }
        return null;
    }
    
}
