package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class IfStartsWithItem extends ConditionalItem {
    String startsWith;
    public IfStartsWithItem (Chain nestedTrue,Chain nestedFalse, String startsWith,String source) {
        super(nestedTrue, nestedFalse);
        this.startsWith=startsWith;
        if(source==null){
            source="%CURRENT%";
        }
        this.source=source;
    }
    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).startsWith(startsWith);
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(startsWith)&&super.isValid();
    }
}
