package com.blalp.chatdirector.common.modules.schedule;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class ScheduleModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("schedule-input", "schedule-cron-input");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "schedule-input":
            return ScheduleInputItem.class;
        case "schedule-cron-input":
            return ScheduleCronItem.class;
        default:
            return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}