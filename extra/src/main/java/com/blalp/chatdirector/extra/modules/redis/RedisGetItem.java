package com.blalp.chatdirector.extra.modules.redis;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class RedisGetItem extends RedisItem {

    @Override
    public Context process(Context context) {
        RedisConnection connection = ((RedisConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(RedisConnections.class)).get(this.connection);
        return new Context(connection.getConnection().get(this.key));
    }
}
