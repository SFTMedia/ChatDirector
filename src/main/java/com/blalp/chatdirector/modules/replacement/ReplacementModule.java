package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class ReplacementModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"Regex","RemoveColors"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "Regex":
                return new RegexItem();
            case "RemoveColors":
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
