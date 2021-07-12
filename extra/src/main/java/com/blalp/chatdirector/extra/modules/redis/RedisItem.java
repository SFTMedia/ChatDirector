package com.blalp.chatdirector.extra.modules.redis;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class RedisItem implements IItem {
    String key, connection;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(key, connection);
    }

    @Override
    public Context process(Context context) {
        return new Context();
    }
}
