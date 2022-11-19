package com.blalp.chatdirector.common.modules.context;

import com.blalp.chatdirector.core.modules.common.PassItem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ContextItem extends PassItem {
    String key;

    @Override
    public boolean isValid() {
        return key != null;
    }

}
