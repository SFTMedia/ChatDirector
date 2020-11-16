package com.blalp.chatdirector.modules.cache;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

public class CacheItem extends PassItem {
    String key;
    public CacheItem(String key) {
        this.key=key;
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(key);
    }
}
