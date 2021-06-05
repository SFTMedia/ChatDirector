package com.blalp.chatdirector.common.modules.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;

import org.junit.jupiter.api.Test;

public class TestSchedule {

    static ChatDirector chatDirector;

    @Test
    public void init() {
        if (chatDirector != null) {
            return;
        }
        chatDirector = new ChatDirector(
                new File(this.getClass().getClassLoader().getResource("modules/schedule/config.yml").getFile()));
        assertTrue(chatDirector.load());

    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    @Test
    public void parseInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("schedule-input"));
        assertEquals(2, chatDirector.getChains().get("schedule-input").getItems().size());
        ScheduleInputItem scheduleInputItem = new ScheduleInputItem();
        scheduleInputItem.delay = 10;
        assertEquals(scheduleInputItem, chatDirector.getChains().get("schedule-input").getItems().get(0));
        scheduleInputItem = new ScheduleInputItem();
        scheduleInputItem.delay = 15;
        scheduleInputItem.period = 100;
        assertEquals(scheduleInputItem, chatDirector.getChains().get("schedule-input").getItems().get(1));
    }

    @Test
    public void parseCronInput() {
        init();
        assertTrue(chatDirector.getChains().containsKey("schedule-cron-input"));
        assertEquals(2, chatDirector.getChains().get("schedule-cron-input").getItems().size());
        ScheduleCronItem fileOutputItem = new ScheduleCronItem();
        fileOutputItem.cron = "* *";
        assertEquals(fileOutputItem, chatDirector.getChains().get("schedule-cron-input").getItems().get(0));
        fileOutputItem = new ScheduleCronItem();
        fileOutputItem.cron = "* * *";
        assertEquals(fileOutputItem, chatDirector.getChains().get("schedule-cron-input").getItems().get(1));
    }
}
