package com.blalp.chatdirector.common.modules.replacement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@JsonDeserialize(using = RegexDeserializer.class)
public class RegexItem implements IItem {
    Map<String, String> pairs = new LinkedHashMap<String, String>();

    @Override
    public Context process(Context context) {
        String output = context.getCurrent();
        for (Entry<String, String> map : pairs.entrySet()) {
            output = output.replaceAll(map.getKey(), map.getValue());
        }
        return new Context(output);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(pairs);
    }

}