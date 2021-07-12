package com.blalp.chatdirector.common.modules.schedule;

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
            TimerTask task = new TimerTask(){
                @Override
                public void run() {
                    ChatDirector.run(item, new Context(), true);
                    timer.schedule(this, item.getDelayUntilNext());
                }
            };
            timer.schedule(task, item.getDelayUntilNext());
        }
        return true;
    }

    @Override
    public boolean unload() {
        timer.cancel();
        return true;
    }
    
}
