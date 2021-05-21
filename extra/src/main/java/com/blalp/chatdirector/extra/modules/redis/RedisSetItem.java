package com.blalp.chatdirector.extra.modules.redis;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
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
public class RedisSetItem extends RedisItem {
    String value;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(value) && super.isValid();
    }
    
    @Override
    public Context process(Context context) {
        RedisConnection connection = ((RedisConnections)ChatDirector.getConfig().getOrCreateDaemon(RedisConnections.class)).get(this.connection);
        connection.getConnection().set(this.key, value);
        return super.process(context);
    }
}
