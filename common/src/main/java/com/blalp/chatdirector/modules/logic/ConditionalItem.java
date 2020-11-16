package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;

public abstract class ConditionalItem extends PassItem {
    
    Chain trueChain;
    Chain falseChain;
    boolean invert=false;
    String source="%CURRENT%";

    @Override
    public Context process(Context context) {
        boolean result= test(context);
        if(invert){
            result=!result;
        }
        ChatDirector.logDebug("Conditional "+this.getClass().getCanonicalName()+" test returned "+result);
        ChatDirector.logDebug("True is "+trueChain+" false is "+falseChain);
        if(result){
            return trueChain.run(context);
        } else {
            return falseChain.run(context);
        }
    }
    
    public abstract boolean test(Context context);
    public ConditionalItem(Chain trueChain,Chain falseChain){
        this.trueChain=trueChain;
        this.falseChain=falseChain;
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(trueChain,falseChain)&&ValidationUtils.hasContent(source);
    }
}
