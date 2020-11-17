package com.blalp.chatdirector.modules.context;

import com.blalp.chatdirector.modules.common.PassItem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ContextItem extends PassItem {
    String context;

    @Override
    public boolean isValid() {
        return context != null;
    }

}
