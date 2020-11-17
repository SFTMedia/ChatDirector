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
@EqualsAndHashCode(callSuper = true)
public class SubStringItem implements IItem {
    public int start = 0;
    public int end = -1;

    @Override
    public Context process(Context context) {
        if (end == -1) {
            return new Context(context.getCurrent().substring(start));
        } else {
            return new Context(context.getCurrent().substring(start, end));
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
