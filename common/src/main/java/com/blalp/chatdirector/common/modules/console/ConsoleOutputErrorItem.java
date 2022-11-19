package com.blalp.chatdirector.common.modules.console;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
public class ConsoleOutputErrorItem implements IItem {

    @Override
    public Context process(Context context) {
        System.err.println(ChatDirector.format(context.getCurrent(), context));
        return new Context();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}