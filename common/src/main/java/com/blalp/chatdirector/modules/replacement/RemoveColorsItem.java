package com.blalp.chatdirector.modules.replacement;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class RemoveColorsItem implements IItem {

    @Override
    public Context process(Context context) {
        return new Context(context.getCurrent().replaceAll("(&|§)([a-z]|[0-9])", ""));
    }

    @Override
    public boolean isValid() {
        return true;
    }

}