package com.blalp.chatdirector.common.modules.logic;

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
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = IfRegexMatchDeserializer.class)
public class IfRegexMatchesItem extends ConditionalItem {
    String match;

    public IfRegexMatchesItem(Chain nestedTrue, Chain nestedFalse, String match) {
        super(nestedTrue, nestedFalse);
        this.match = match;
    }

    @Override
    public boolean test(Context context) {
        return (ChatDirector.format(source, context)).matches((ChatDirector.format(match, context)));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(match) && super.isValid();
    }
}