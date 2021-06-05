package com.blalp.chatdirector.common.modules.schedule;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ScheduleInputItem extends PassItem {

    // Milliseconds
    int delay;
    int period = -1;

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(delay,period);
    }

    public ScheduleInputItem () {
        ((ScheduleInputDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(ScheduleInputDaemon.class)).addItem(this);
    }

    public void getDelayUntilNext() {
    }
    
}
