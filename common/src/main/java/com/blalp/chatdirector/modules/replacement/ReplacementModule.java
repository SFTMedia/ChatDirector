package com.blalp.chatdirector.modules.replacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ReplacementModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"regex","remove-colors","resolve-colors","sub-string","to-lower","to-upper","to-word"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "regex":
                return new RegexItem((ArrayList<Map<String,String>>)config);
            case "remove-colors":
                return new RemoveColorsItem();
            case "resolve-colors":
                return new ResolveColorsItem();
            case "sub-string":
                SubStringItem subStringItem = new SubStringItem();
                if(((HashMap<String,Object>)config).containsKey("start")){
                    subStringItem.start= (int) ((HashMap<String, Object>) config).get("start");
                }
                if(((HashMap<String,Object>)config).containsKey("end")){
                    subStringItem.start= (int) ((HashMap<String, Object>) config).get("end");
                }
                return subStringItem;
            case "to-lower":
                return new ToLowerItem();
            case "to-upper":
                return new ToUpperItem();
            case "to-word":
                return new ToWordItem();
            default:
                return null;
        }
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }
    
}
