package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class IfRegexMatchesItem extends ConditionalItem {
    String regex;
    public IfRegexMatchesItem(Chain nestedTrue, Chain nestedFalse,String regex) {
        super(nestedTrue, nestedFalse);
        this.regex=regex;
    }

    @Override
    public boolean test(Context context) {
        return (ChatDirector.format(source, context)).matches((ChatDirector.format(regex, context)));
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(regex)&&super.isValid();
    }
}