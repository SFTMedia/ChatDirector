package com.blalp.chatdirector.core.modules.common;

import com.blalp.chatdirector.core.configuration.TimedLoad;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ReloadItem implements IItem {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context process(Context context) {
        new Thread(new TimedLoad()).start();
        return new Context();
    }

}
