package com.blalp.chatdirector.common.modules.random;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
public class RandomItem implements IItem {

    int min;
    int max;

    @Override
    public Context process(Context context) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return new Context(String.valueOf(randomNum));
    }

    @Override
    public boolean isValid() {
        return max > min;
    }
}
