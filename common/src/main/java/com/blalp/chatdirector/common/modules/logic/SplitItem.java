package com.blalp.chatdirector.common.modules.logic;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = SplitItemDeserializer.class)
@JsonSerialize(using = SplitItemSerializer.class)
public class SplitItem extends PassItem {
    Map<String, Chain> chains = new HashMap<String, Chain>();

    @Override
    public Context process(Context context) {
        for (Chain chain : chains.values()) {
            new Thread(chain).start();
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(chains);
    }
}