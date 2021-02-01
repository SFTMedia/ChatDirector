package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ConditionalItem extends PassItem {

    Chain yesChain;
    Chain noChain;
    boolean invert = false;
    String source = "%CURRENT%";

    @Override
    public Context process(Context context) {
        boolean result = test(context);
        if (invert) {
            result = !result;
        }
        ChatDirector.logDebug("Conditional " + this.getClass().getCanonicalName() + " test returned " + result);
        ChatDirector.logDebug("True is " + yesChain + " false is " + noChain);
        if (result) {
            return yesChain.run(context);
        } else {
            return noChain.run(context);
        }
    }

    public abstract boolean test(Context context);

    public ConditionalItem(Chain trueChain, Chain falseChain) {
        this.yesChain = trueChain;
        this.noChain = falseChain;
    }

    @Override
    public boolean isValid() {
        return (ValidationUtils.hasContent(yesChain)||ValidationUtils.hasContent(noChain)) && ValidationUtils.hasContent(source);
    }
}
