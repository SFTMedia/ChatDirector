package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class ToWordItem implements IItem {

    @Override
    public Context process(Context context) {
        String output = "";
        for(String word: context.getCurrent().split(" ")){
            if(!output.isBlank()){
                output+=" ";
            }
            output+=word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase();
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
}
