package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class IfContainsItem extends ConditionalItem {
    String contains;
    public IfContainsItem(Chain nestedTrue,Chain nestedFalse,String contains){
        super(nestedTrue,nestedFalse);
        this.contains=contains;
    }

    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).contains(ChatDirector.format(contains, context));
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(contains)&&super.isValid();
    }
}