package com.blalp.chatdirector.modules.logic;

import java.util.Map;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.model.IItem;

public abstract class ConditionalItem extends PassItem {
    
    IItem nestedTrue;
    IItem nestedFalse;

    @Override
    public void work(String string, Map<String,String> context) {
        if(string.isEmpty()){
            return;
        }
        if(test(string,context)){
            nestedTrue.work(string,context);
        } else {
            nestedFalse.work(string,context);
        }
        next.work(string, context);
    }
    public abstract boolean test(String string,Map<String,String> context);
    public ConditionalItem(IItem nestedTrue,IItem nestedFalse){
        this.nestedFalse=nestedFalse;
        this.nestedTrue=nestedTrue;
    }
}
