package com.blalp.chatdirector.common.modules.logic;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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