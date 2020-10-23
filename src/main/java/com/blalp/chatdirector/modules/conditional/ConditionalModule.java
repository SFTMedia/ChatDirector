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
        return new String[]{"IfContains","IfEquals","IfRegexMatches"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "IfContains":
                return new IfContainsItem();
            case "IfEquals":
                return new IfEqualsItem();
            case "IfRegexMatches":
                return new IfRegexMatchesItem();
        }
        return null;
    }
    
}
