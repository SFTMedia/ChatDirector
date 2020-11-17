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
@JsonDeserialize(using = IfEndsWithDeserializer.class)
public class IfEndsWithItem extends ConditionalItem {
    String endsWith;

    public IfEndsWithItem(Chain nestedTrue, Chain nestedFalse, String endsWith, String source) {
        super(nestedTrue, nestedFalse);
        this.endsWith = endsWith;
        if (source == null) {
            source = "%CURRENT%";
        }
        this.source = source;
    }

    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).endsWith(endsWith);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(endsWith) && super.isValid();
    }
}
