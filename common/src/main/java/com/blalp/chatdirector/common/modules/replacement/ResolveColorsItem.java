package com.blalp.chatdirector.common.modules.replacement;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
public class ResolveColorsItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().replaceAll("&([a-z]|[0-9])", "§$1"));
    }

    @Override
    public boolean isValid() {
        return true;
    }

}