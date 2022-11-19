package com.blalp.chatdirector.common.modules.cache;

import com.blalp.chatdirector.common.modules.logic.ConditionalItem;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheIfItem extends ConditionalItem {
    String key;

    @Override
    public boolean test(Context context) {
        CacheStore cacheStore = (CacheStore) ChatDirector.getConfig().getOrCreateDaemon(CacheStore.class);
        return cacheStore.containsKey(ChatDirector.format(key, context));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(key) && super.isValid();
    }

}
