package com.blalp.chatdirector.modules.common;

import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
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
