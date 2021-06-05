package com.blalp.chatdirector.core.modules.common;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = EchoItemDeserializer.class)
@JsonSerialize(using = EchoItemSerializer.class)
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
