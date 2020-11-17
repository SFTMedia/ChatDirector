package com.blalp.chatdirector.modules.replacement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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