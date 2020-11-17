package com.blalp.chatdirector.modules.console;

import com.blalp.chatdirector.ChatDirector;
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