package com.blalp.chatdirector.extra.modules.mqtt;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
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
