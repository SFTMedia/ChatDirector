package com.blalp.chatdirector.legacyConfig.modules.schedule;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class ScheduleLegacyModule implements ILegacyModule {
    
    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("schedule-input","schedule-cron-input");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
            case "schedule-input":
                return ScheduleInputItem_v0_2_0.class;
            case "schedule-cron-input":
                return ScheduleCronItem_v0_2_0.class;
            default:
                return null;
        }
    }
}
