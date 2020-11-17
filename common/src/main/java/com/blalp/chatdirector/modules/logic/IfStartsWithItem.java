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
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = IfStartsWithDeserializer.class)
public class IfStartsWithItem extends ConditionalItem {
    String startsWith;

    public IfStartsWithItem(Chain nestedTrue, Chain nestedFalse, String startsWith, String source) {
        super(nestedTrue, nestedFalse);
        this.startsWith = startsWith;
        if (source == null) {
            source = "%CURRENT%";
        }
        this.source = source;
    }

    @Override
    public boolean test(Context context) {
        return ChatDirector.format(source, context).startsWith(startsWith);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(startsWith) && super.isValid();
    }
}
