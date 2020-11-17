package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = EchoItemDeserializer.class)
public class EchoItem implements IItem {
    public String format;

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
