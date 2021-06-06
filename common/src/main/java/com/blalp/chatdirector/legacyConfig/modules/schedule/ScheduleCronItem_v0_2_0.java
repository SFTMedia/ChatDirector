package com.blalp.chatdirector.legacyConfig.modules.schedule;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class ScheduleCronItem_v0_2_0 implements ILegacyItem {

    String cron;
    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return null;
    }

    @Override
    public String name() {
        return "schedule-cron-input";
    }
    
}
