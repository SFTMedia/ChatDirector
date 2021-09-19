package com.blalp.chatdirector.common.modules.schedule;

import java.util.Date;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleCronItem extends PassItem {

    // Format <seconds> <minutes> <hours> <day-of-week> <month> <year>
    // NOTE: DOES NOT INCLUDE DAY OF THE MONTH
    // Characters:
    // * (any)
    // - Range
    // , Multiple Values
    // / Increment by
    String cron;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(cron);
    }

    public ScheduleCronItem() {
        ((ScheduleCronDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(ScheduleCronDaemon.class))
                .addItem(this);
    }

    public long getDelayUntilNext(Date start) {
        Date next = new CronCalculator(cron, start).getNextDate();
        return next.getTime()-start.getTime();
    }
}
