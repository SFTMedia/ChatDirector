package com.blalp.chatdirector.common.modules.logic;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class IfEqualsItem extends ConditionalItem {
    String equals;
    boolean ignoreCase = false;

    public IfEqualsItem(Chain nestedTrue, Chain nestedFalse, String equals) {
        super(nestedTrue, nestedFalse);
        this.equals = equals;
    }

    @Override
    public boolean test(Context context) {
        return (ChatDirector.format(source, context).equals(ChatDirector.format(equals, context)) || (ignoreCase
                && ChatDirector.format(source, context).equalsIgnoreCase(ChatDirector.format(equals, context))));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(equals) && super.isValid();
    }

}