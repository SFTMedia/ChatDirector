package com.blalp.chatdirector.common.modules.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Date;

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
        ScheduleCronItem cronInputItem = new ScheduleCronItem();
        cronInputItem.cron = "* *";
        assertEquals(cronInputItem, chatDirector.getChains().get("schedule-cron-input").getItems().get(0));
        cronInputItem = new ScheduleCronItem();
        cronInputItem.cron = "* * *";
        assertEquals(cronInputItem, chatDirector.getChains().get("schedule-cron-input").getItems().get(1));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestSecondsCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("*", start);
        expected = new Date(2000, 1, 2, 1, 2, 4);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("0", start);
        expected = new Date(2000, 1, 2, 1, 3, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("*/5", start);
        expected = new Date(2000, 1, 2, 1, 2, 5);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("1,17", start);
        expected = new Date(2000, 1, 2, 1, 2, 17);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("3-17", start);
        expected = new Date(2000, 1, 2, 1, 2, 4);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("12-17", start);
        expected = new Date(2000, 1, 2, 1, 2, 12);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestMinutesCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* 0", start);
        expected = new Date(2000, 1, 2, 2, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* */5", start);
        expected = new Date(2000, 1, 2, 1, 5, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* 1,17", start);
        expected = new Date(2000, 1, 2, 1, 17, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* 3-17", start);
        expected = new Date(2000, 1, 2, 1, 3, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* 12-17", start);
        expected = new Date(2000, 1, 2, 1, 12, 0);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestHoursCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* * 0", start);
        expected = new Date(2000, 1, 3, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * */5", start);
        expected = new Date(2000, 1, 2, 5, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * 5,17", start);
        expected = new Date(2000, 1, 2, 5, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * 3-17", start);
        expected = new Date(2000, 1, 2, 3, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * 12-17", start);
        expected = new Date(2000, 1, 2, 12, 0, 0);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestDayOfMonthCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* * * 0", start);
        expected = new Date(2000, 1, 28, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * 5", start);
        expected = new Date(2000, 1, 5, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * */5", start);
        expected = new Date(2000, 1, 5, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * 1,17", start);
        expected = new Date(2000, 1, 17, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * 3-17", start);
        expected = new Date(2000, 1, 3, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * 6-7", start);
        expected = new Date(2000, 1, 6, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * 2", start);
        expected = new Date(2000, 1, 2, 1, 2, 4);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestMonthCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* * * * 0", start);
        expected = new Date(2001, 0, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * 0/5", start);
        expected = new Date(2000, 5, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * 2,8", start);
        expected = new Date(2000, 2, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * 3-17", start);
        expected = new Date(2000, 3, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * 12-17", start);
        expected = new Date(2000, 12, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestDayOfTheWeekCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* * * * * 1", start);
        expected = new Date(2000, 1, 5, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * */3", start);
        expected = new Date(2000, 1, 3, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * 3,6", start);
        expected = new Date(2000, 1, 3, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * 5-6", start);
        expected = new Date(2000, 1, 2, 1, 2, 4);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * 1-3", start);
        expected = new Date(2000, 1, 5, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestYearCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        calc = new CronCalculator("* * * * * * 2222", start);
        expected = new Date(2222, 0, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * * 1/5", start);
        expected = new Date(2001, 0, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * * 2006,2009", start);
        expected = new Date(2006, 0, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * * 1990-2500", start);
        expected = new Date(2000, 1, 2, 1, 2, 4);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("* * * * * * 2015-2055", start);
        expected = new Date(2015, 0, 1, 0, 0, 0);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void unitTestComplexCron() {
        ChatDirector test = new ChatDirector();
        Date start = new Date(2000, 1, 2, 1, 2, 3);
        CronCalculator calc;
        Date expected;
        // At noon daily
        calc = new CronCalculator("0 12 * * *", start);
        expected = new Date(2000, 1, 2, 1, 12, 0);
        assertEquals(expected, calc.getNextDate());
        calc = new CronCalculator("3 * * * *", start);
        expected = new Date(2000, 1, 2, 1, 3, 3);
        assertEquals(expected, calc.getNextDate());
    }

    @Test
    public void unitTestDayOfWeekFormat() {

    }

    @Test
    public void unitTestMonthFormat() {

    }
}
