package com.blalp.chatdirector.common.modules.cache;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CacheGetItem extends CacheItem {

    @Override
    public Context process(Context context) {
        CacheStore cacheStore = (CacheStore) ChatDirector.getConfig().getOrCreateDaemon(CacheStore.class);
        if (cacheStore.containsKey(ChatDirector.format(this.key, context))) {
            return new Context(cacheStore.getValue(ChatDirector.format(this.key, context)));
        }
        return new Context();
    }

}
