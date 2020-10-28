package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.modules.common.PassItem;

public class ContextItem extends PassItem {
    String contextName;
    public ContextItem(String context){
        this.contextName=context;
    }
    
}
