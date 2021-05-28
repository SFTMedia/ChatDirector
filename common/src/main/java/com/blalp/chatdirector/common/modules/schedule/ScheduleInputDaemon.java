package com.blalp.chatdirector.common.modules.schedule;

import java.util.Timer;
import java.util.TimerTask;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ItemDaemon;

public class ScheduleInputDaemon extends ItemDaemon {

    Timer timer = new Timer("ChatDirector Timer");

    @Override
    public boolean load() {
        for (ScheduleInputItem item : this.getItems().toArray(new ScheduleInputItem[]{})) {
            TimerTask task = new TimerTask(){
                @Override
                public void run() {
                    ChatDirector.run(item, new Context(), true);
                }
            };
            if(item.period==-1){
                timer.schedule(task, item.delay);
            } else {
                timer.scheduleAtFixedRate(task, item.delay, item.period);
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        timer.cancel();
        return true;
    }
    
}
