package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.modules.common.PassItem;

public class ContextItem extends PassItem {
    String contextName;
    public ContextItem(String context){
        this.contextName=context;
    }

    @Override
    public boolean isValid() {
        return contextName!=null;
    }
    
}
