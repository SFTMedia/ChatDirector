package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
public class ToUpperItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().toUpperCase());
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
