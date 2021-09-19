package com.blalp.chatdirector.common.modules.schedule;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ItemDaemon;

public class ScheduleCronDaemon extends ItemDaemon {
    
    Timer timer = new Timer("ChatDirector Cron Timer");

    @Override
    public boolean load() {
        for (ScheduleCronItem item : this.getItems().toArray(new ScheduleCronItem[]{})) {
            timer.schedule(newTimer(item), item.getDelayUntilNext(new Date()));
        }
        return true;
    }

    private TimerTask newTimer(ScheduleCronItem item) {
        return new TimerTask(){
            @Override
            public void run() {
                ChatDirector.run(item, new Context(), true);
                timer.schedule(newTimer(item), item.getDelayUntilNext(new Date()));
            }
        };
    }

    @Override
    public boolean unload() {
        timer.cancel();
        return true;
    }
    
}
