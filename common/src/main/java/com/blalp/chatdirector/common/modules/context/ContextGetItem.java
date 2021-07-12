package com.blalp.chatdirector.common.modules.context;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ContextGetItem extends ContextItem {

    @Override
    public Context process(Context context) {
        return new Context(context.get(ChatDirector.format(this.key, context)));
    }

}
