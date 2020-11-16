package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class IfEqualsItem extends ConditionalItem {
    String equals;
    boolean ignoreCase=false;
    public IfEqualsItem(Chain nestedTrue,Chain nestedFalse,String equals) {
        super(nestedTrue, nestedFalse);
        this.equals=equals;
    }

    @Override
    public boolean test(Context context) {
        return (ChatDirector.format(source, context).equals(ChatDirector.format(equals,context))||(ignoreCase&&ChatDirector.format(source, context).equalsIgnoreCase(ChatDirector.format(equals,context))));
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(equals)&&super.isValid();
    }
    
}