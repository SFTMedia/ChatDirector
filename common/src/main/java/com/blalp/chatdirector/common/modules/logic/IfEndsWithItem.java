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
public class IfEndsWithItem extends ConditionalItem {
    String ends;

    public IfEndsWithItem(Chain nestedTrue, Chain nestedFalse, String endsWith, String source) {
        super(nestedTrue, nestedFalse);
        this.ends = endsWith;
        if (source == null) {
            source = "%CURRENT%";
        }
        this.source = source;
    }

    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).endsWith(ends);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(ends) && super.isValid();
    }
}
