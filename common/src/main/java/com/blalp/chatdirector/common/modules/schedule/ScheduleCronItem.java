package com.blalp.chatdirector.common.modules.schedule;

import java.util.Date;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ScheduleCronItem extends PassItem {

    // Format <seconds> <minutes> <hours> <day-of-month> <month>
    // NOTE: DOES NOT INCLUDE DAY OF THE WEEK OR YEAR
    // Characters:
        // * (any)
        // - Range
        // , Multiple Values
        // / Increment by
    String cron;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(cron);
    }

    public ScheduleCronItem () {
        ((ScheduleCronDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(ScheduleCronDaemon.class)).addItem(this);
    }

    @SuppressWarnings("deprecation")
    public long getDelayUntilNext(){
        Date date = new Date();
        Date output = new Date();
        // Convert to seconds
        String[] parts = cron.split(" ");
        // Seconds
        if(parts.length>0){
            output.setSeconds(getNumericNext(parts[0],date.getSeconds()+1));
        }
        // Check overflow
        if(output.getSeconds()>date.getSeconds()){
            date.setMinutes(date.getMinutes()+1);
        }
        // Minutes
        if(parts.length>1){
            output.setMinutes(getNumericNext(parts[1],date.getMinutes()));
        }
        // Check overflow
        if(output.getMinutes()>date.getMinutes()){
            date.setHours(date.getHours()+1);
        }
        // Hours
        if(parts.length>2){
            output.setHours(getNumericNext(parts[2],date.getHours()));
        }
        // Check overflow
        if(output.getHours()>date.getHours()){
            date.setDate(date.getDate()+1);
        }
        // Day
        if(parts.length>3){
            output.setDate(getNumericNext(parts[3],date.getDay()));
        }
        // Check overflow
        if(output.getDay()>date.getDay()){
            date.setMonth(date.getMonth()+1);
        }
        // Month
        if(parts.length>4){
            output.setMonth(getNumericNext(parts[4],date.getMonth()));
        }

        // Make sure that the time is the the future just in case.
        // Take * * * * * * for example, if the function starts running before the minute but ends after it will have a time in the past.
        if(output.getTime()<date.getTime()){
            return date.getTime()+1000;
        }
        return output.getTime();
    }

    public int getNumericNext(String timeUnit,int currentTime){
        if(timeUnit.equals("*")){
            return currentTime;
        }
        if(timeUnit.startsWith("*/")){
            int interval = Integer.parseInt(timeUnit.substring(2));
            return currentTime+interval-currentTime%interval;
        }
        if(timeUnit.contains("-")){
            int start = Integer.parseInt(timeUnit.split("-")[0]);
            int end = Integer.parseInt(timeUnit.split("-")[0]);
            if(start<currentTime){
                if(currentTime<end) {
                    return currentTime;
                } else {
                    return start;
                }
            } else {
                return start;
            }
        }
        if(timeUnit.contains(",")){
            int diff = Integer.MAX_VALUE;
            for (String option : timeUnit.split(",")) {
                if(Integer.parseInt(option)-currentTime<diff){
                    diff = Integer.parseInt(option)-currentTime;
                }
            }
            return currentTime+diff;
        }
        ChatDirector.getLogger().log(Level.WARNING, "Could not interpret "+timeUnit);
        return 0;
    }
    
}
