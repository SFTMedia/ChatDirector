package com.blalp.chatdirector.modules.logic;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = IfRegexMatchDeserializer.class)
public class IfRegexMatchesItem extends ConditionalItem {
    String regex;

    public IfRegexMatchesItem(Chain nestedTrue, Chain nestedFalse, String regex) {
        super(nestedTrue, nestedFalse);
        this.regex = regex;
    }

    @Override
    public boolean test(Context context) {
        return (ChatDirector.format(source, context)).matches((ChatDirector.format(regex, context)));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(regex) && super.isValid();
    }
}