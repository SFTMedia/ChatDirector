package com.blalp.chatdirector.modules.replacement;

import java.util.ArrayList;
import java.util.Map;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ReplacementModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"regex","remove-colors"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "regex":
                return new RegexItem((ArrayList<Map<String,String>>)config);
            case "remove-colors":
                return new RemoveColorsItem();
        }
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }
    
}
