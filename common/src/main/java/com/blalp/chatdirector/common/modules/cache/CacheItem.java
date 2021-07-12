package com.blalp.chatdirector.common.modules.cache;

import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheItem extends PassItem {
    String key;

    public boolean isValid() {
        return ValidationUtils.hasContent(key);
    }
}
