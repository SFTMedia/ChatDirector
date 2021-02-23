package com.blalp.chatdirector.common.modules.cache;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CacheGetItem extends CacheItem {

    @Override
    public Context process(Context context) {
        if (CacheStore.containsKey(ChatDirector.format(this.key, context))) {
            return new Context(CacheStore.getValue(ChatDirector.format(this.key, context)));
        }
        return new Context();
    }

}
