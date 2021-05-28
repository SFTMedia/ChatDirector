package com.blalp.chatdirector.extra.modules.mqtt;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MQTTItem implements IItem {

    String connection;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(connection);
    }

    @Override
    public Context process(Context context) {
        return new Context();
    }

}
