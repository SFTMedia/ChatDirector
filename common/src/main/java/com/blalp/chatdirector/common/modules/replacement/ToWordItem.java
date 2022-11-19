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
public class ToWordItem implements IItem {

    @Override
    public Context process(Context context) {
        String output = "";
        for (String word : context.getCurrent().split(" ")) {
            if (!output.trim().isEmpty()) {
                output += " ";
            }
            output += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
