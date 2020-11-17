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
@JsonDeserialize(using = IfContainsItemDeserializer.class)
public class IfContainsItem extends ConditionalItem {
    String contains;

    public IfContainsItem(Chain nestedTrue, Chain nestedFalse, String contains) {
        super(nestedTrue, nestedFalse);
        this.contains = contains;
    }

    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).contains(ChatDirector.format(contains, context));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(contains) && super.isValid();
    }
}