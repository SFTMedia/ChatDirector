package com.blalp.chatdirector.core.modules.common;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = EchoItemDeserializer.class)
public class EchoItem implements IItem {
    String format;

    public EchoItem(String format) {
        this.format = format;
    }

    @Override
    public boolean isValid() {
        return format != null;
    }

    @Override
    public Context process(Context context) {
        return new Context(ChatDirector.format(format, context));
    }

}
