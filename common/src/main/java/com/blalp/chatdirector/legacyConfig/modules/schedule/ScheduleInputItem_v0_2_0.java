package com.blalp.chatdirector.legacyConfig.modules.schedule;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class ScheduleInputItem_v0_2_0 implements ILegacyItem {

    // Milliseconds
    int delay;
    int period = -1;
    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Version nextUpdateVersion() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "schedule-input";
    }
    
}
