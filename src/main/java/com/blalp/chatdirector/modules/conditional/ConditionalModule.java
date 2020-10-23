package com.blalp.chatdirector.modules.conditional;

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
        return new String[]{"if-contains","if-equals","if-regex-match"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "if-contains":
                return new IfContainsItem();
            case "if-equals":
                return new IfEqualsItem();
            case "if-regex-match":
                return new IfRegexMatchesItem();
        }
        return null;
    }
    
}
