package com.blalp.chatdirector.common.modules.schedule;

import java.util.Date;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;

@SuppressWarnings("deprecation")
public class CronCalculator {
    private Date start;
    private Date output;
    /*
        This Cron
        <second>
        <minute>
        <hour>
        <day of week>
        <month>
        <year>


        Normal Cron
        <second>
        <minute>
        <hour>
        <day of month>
        <month>
        <day of week>
        <year>
    */
    private String[] cron;

    public CronCalculator(String cron, Date start) {
        this.cron = cron.split(" ");
        this.start = start;
        this.output = (Date) this.start.clone();
    }

    private void enforceSeconds() {
        if (cron.length > 0) {
            int newSeconds = getNumericNext(cron[0], output.getSeconds());
            if (output.getSeconds() > newSeconds) {
                output.setMinutes(output.getMinutes() + 1);
            }
            output.setSeconds(newSeconds);
        }
    }

    private void resetSeconds() {
        output.setSeconds(0);
        this.enforceSeconds();
    }

    private void enforceMinutes() {
        if (cron.length > 1) {
            int newMinutes = getNumericNext(cron[1], output.getMinutes());
            if (output.getMinutes() > newMinutes) {
                output.setHours(output.getHours() + 1);
            }
            output.setMinutes(newMinutes);

            // Minutes changed, get earliest second
            if (start.getMinutes() != output.getMinutes()) {
                this.resetSeconds();
            }
        }
    }

    private void resetMinutes() {
        resetSeconds();
        output.setMinutes(0);
        enforceMinutes();
    }

    private void enforceHours() {
        if (cron.length > 2) {
            int newHours = getNumericNext(cron[2], output.getHours());
            if (output.getHours() > newHours) {
                output.setDate(output.getDate() + 1);
            }
            output.setHours(newHours);

            if (start.getHours() != output.getHours()) {
                resetMinutes();
            }
        }
    }

    private void resetHours() {
        enforceMinutes();
        output.setHours(0);
        enforceHours();
    }

    private void enforceDOW() {
        if (cron.length > 3) {
            int dow = getNumericNext(cron[3], output.getDay());
            if (dow < output.getDay()) {
                dow += 7;
            } else if (dow > 7) {
                // Couldn't find a valid for this week, try again next week.
                output.setDate(output.getDate()+7-output.getDay());
                enforceDOW();
                return;
            }
            int newDate = output.getDate() - output.getDay() + dow;
            if (output.getDate() > newDate) {
                output.setMonth(output.getMonth() + 1);
            }
            output.setDate(newDate);

            if (start.getDate() != output.getDate()) {
                resetHours();
            }
        }
    }

    private void resetDate() {
        resetHours();
        output.setDate(0);
        enforceDOW();
        // TODO: Date?
    }

    private void enforceMonths() {
        if (cron.length > 4) {
            int newMonths = getNumericNext(cron[4], output.getMonth());
            if (output.getMonth() > newMonths) {
                output.setYear(output.getYear() + 1);
            }
            output.setMonth(newMonths);

            if (start.getMonth()!=output.getMonth()) {
                resetDate();
            }
        }
    }

    private void resetMonths() {
        resetDate();
        output.setMonth(0);
        enforceMonths();
    }

    private void enforceYears() {
        // Month
        if (cron.length > 5) {
            int newYear = getNumericNext(cron[5], output.getYear());
            if (output.getYear() > newYear) {
                // In the PAST! YIKES!
            }
            output.setYear(newYear);

            if (start.getYear() != output.getYear()) {
                resetMonths();
            }
        }
    }

    public Date getNextDate() {

        this.enforceSeconds();
        this.enforceMinutes();
        this.enforceHours();
        this.enforceDOW();
        this.enforceMonths();
        this.enforceYears();

        ChatDirector.getLogger().log(Level.INFO, "The starts " + output.toString() + " " + start.toString());

        // Make sure that the time is the the future just in case.
        // Take * * * * * * for example, if the function starts running before the
        // minute but ends after it will have a time in the past.
        if (output.getTime() <= start.getTime()) {
            output.setTime(start.getTime() + 1000);
        }
        return output;
    }

    protected int getNumericNext(String timeUnit, int currentTime) {
        if (timeUnit.equals("*")) {
            return currentTime;
        }
        if (timeUnit.startsWith("*/")) {
            int interval = Integer.parseInt(timeUnit.substring(2));
            return currentTime + interval - currentTime % interval;
        }
        if (timeUnit.contains("-")) {
            int start = Integer.parseInt(timeUnit.split("-")[0]);
            int end = Integer.parseInt(timeUnit.split("-")[1]);
            if (start < currentTime) {
                if (currentTime < end) {
                    return currentTime;
                } else {
                    return start;
                }
            } else {
                return start;
            }
        }
        if (timeUnit.contains(",")) {
            int diff = Integer.MAX_VALUE;
            for (String option : timeUnit.split(",")) {
                int candidateDiff = Integer.parseInt(option) - currentTime;
                if (candidateDiff > 0 && candidateDiff < diff) {
                    diff = candidateDiff;
                }
            }
            return currentTime + diff;
        }
        try {
            return Integer.parseInt(timeUnit);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ChatDirector.getLogger().log(Level.WARNING, "Could not interpret " + timeUnit);
        return 0;
    }
}
