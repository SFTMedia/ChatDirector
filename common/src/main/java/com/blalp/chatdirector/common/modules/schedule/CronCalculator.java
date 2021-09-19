package com.blalp.chatdirector.common.modules.schedule;

import java.util.Date;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;

@SuppressWarnings("deprecation")
public class CronCalculator {
    private Date start;
    private Date output;
    // <second> <minute> <hour> <day of month> <month> <day of week> <year>
    // <second> 0-59
    // <minute> 0-59
    // <hour> 0-23

    // <day of month> 1-end (0 is an alias for last day of month)
    // <month> 0-11
    // <day of week> SUN|MON|TUE|WED|THU|FRI|SAT|?|0-6
    // <year> 0-?

    // Formats:
    // * Anything
    // <remainder>/<modulo> Remainder after modulo operation
    // <number>-<number> Interval
    // <number>,<number> Multiple possible

    // DOES NOT SUPPORT:
    // <day of month> The #th DoW (i.e. 1#5)
    // <day of month> days from end of month (i.e. L-1)
    // <day of month> weekday (i.e. W)

    private String[] cron;

    public CronCalculator(String cron, Date start) {
        this.cron = cron.split(" ");
        enforceFormat();
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

    private void enforceDate() {
        int previousDate = -1;
        int previousMonth = -1;
        // While the enforce functions change the date or month, continue enforcing
        while (previousDate != output.getDate() || previousMonth != output.getMonth()) {
            previousDate = output.getDate();
            previousMonth = output.getMonth();
            this.enforceDayOfMonth();
            this.enforceDayOfWeek();
        }
    }

    private void enforceDayOfWeek() {
        if (cron.length > 5) {
            int dow = getNumericNext(cron[5], output.getDay());
            if (dow < output.getDay()) {
                dow += 7;
            } else if (dow > 7) {
                // Couldn't find a valid for this week, try again next week.
                output.setDate(output.getDate() + 7 - output.getDay());
                enforceDayOfWeek();
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

    private void enforceDayOfMonth() {
        if (cron.length > 3) {
            int newDate = getNumericNext(cron[3], output.getDate());
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
        output.setDate(1);
        enforceDate();
    }

    private void enforceMonths() {
        if (cron.length > 4) {
            int newMonths = getNumericNext(cron[4], output.getMonth());
            if (output.getMonth() > newMonths) {
                output.setYear(output.getYear() + 1);
            }
            output.setMonth(newMonths);

            if (start.getMonth() != output.getMonth()) {
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
        if (cron.length > 6) {
            int newYear = getNumericNext(cron[6], output.getYear());
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
        this.enforceDate();
        this.enforceMonths();
        this.enforceYears();

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
        if (timeUnit.contains("/")) {
            int interval = Integer.parseInt(timeUnit.split("/")[1]);
            String remainderStr = timeUnit.split("/")[0];
            int remainder;
            if (remainderStr.equals("*")) {
                remainder = 0;
            } else {
                remainder = Integer.parseInt(remainderStr);
            }
            if (currentTime % interval != remainder) {
                int toNextRemainder = remainder - currentTime % interval;
                if (toNextRemainder < 0) {
                    toNextRemainder += interval;
                }
                return currentTime + toNextRemainder;
                // 2006-0
            } else {
                return currentTime;
            }
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
                if (candidateDiff >= 0 && candidateDiff < diff) {
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

    private void enforceFormat() {
        enforceDayOfWeekFormat();
        enforceMonthFormat();
        enforceDayOfMonthFormat();
    }

    private void enforceDayOfWeekFormat() {
        if (cron.length > 5) {
            String dayOfWeek = cron[5].toUpperCase();
            dayOfWeek = dayOfWeek.replaceAll("SUN?D?A?Y?", "0");
            dayOfWeek = dayOfWeek.replaceAll("MO?N?D?A?Y?", "1");
            dayOfWeek = dayOfWeek.replaceAll("TUE?S?D?A?Y?", "2");
            dayOfWeek = dayOfWeek.replaceAll("WE?D?N?E?S?D?A?Y?", "3");
            dayOfWeek = dayOfWeek.replaceAll("THU?R?S?D?A?Y?", "4");
            dayOfWeek = dayOfWeek.replaceAll("FR?I?D?A?Y?", "5");
            dayOfWeek = dayOfWeek.replaceAll("SAT?U?R?D?A?Y?", "6");
            // For those normal cron uses out there
            dayOfWeek = dayOfWeek.replaceAll("\\?", "*");
            cron[5] = dayOfWeek;
        }
    }

    private void enforceDayOfMonthFormat() {
        if (cron.length > 3) {
            String dayOfWeek = cron[3].toUpperCase();
            dayOfWeek = dayOfWeek.replaceAll("L", "0");
            cron[3] = dayOfWeek;
        }
    }

    private void enforceMonthFormat() {
        if (cron.length > 4) {
            String dayOfWeek = cron[4].toUpperCase();
            dayOfWeek = dayOfWeek.replaceAll("\\?", "*");
            dayOfWeek = dayOfWeek.replaceAll("JA?N?U?A?R?Y?", "0");
            dayOfWeek = dayOfWeek.replaceAll("FE?B?U?A?R?Y?", "1");
            dayOfWeek = dayOfWeek.replaceAll("MARC?H?", "2");
            dayOfWeek = dayOfWeek.replaceAll("APR?I?L?", "3");
            dayOfWeek = dayOfWeek.replaceAll("MAY", "4");
            dayOfWeek = dayOfWeek.replaceAll("JUNE?", "5");
            dayOfWeek = dayOfWeek.replaceAll("JULY?", "6");
            dayOfWeek = dayOfWeek.replaceAll("AUG?U?S?T?", "7");
            dayOfWeek = dayOfWeek.replaceAll("SE?PT?E?M?B?E?R?", "9");
            dayOfWeek = dayOfWeek.replaceAll("NO?V?E?M?B?E?R?", "10");
            dayOfWeek = dayOfWeek.replaceAll("DE?C?E?M?B?E?R?", "11");
            cron[4] = dayOfWeek;
        }
    }
}
