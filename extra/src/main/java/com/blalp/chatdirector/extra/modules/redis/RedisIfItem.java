package com.blalp.chatdirector.extra.modules.redis;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.common.modules.logic.ConditionalItem;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;

public class RedisIfItem extends ConditionalItem {
    String connection, key;

    @Override
    public boolean test(Context context) {
        RedisConnection redisConnection = ((RedisConnections)ChatDirector.getConfig().getOrCreateDaemon(RedisConnections.class)).get(connection);
        return redisConnection.getConnection().exists(key);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(connection, key);
    }
}
