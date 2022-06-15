package com.talkingclock.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TalkingClockService {

    private static final String[] HOUR_MIN_WORDS = {
            "Zero", "One", "Two", "Three", "Four",
            "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen",
            "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen", "Twenty", "Twenty one",
            "Twenty two", "Twenty three", "Twenty four",
            "Twenty five", "Twenty six", "Twenty seven",
            "Twenty eight", "Twenty nine"
    };
    private static final String QUARTER_PAST = "Quarter past ";
    private static final String O_CLOCK = " o'clock";
    private static final String HALF_PAST = "Half past ";
    private static final String QUARTER_TO = "Quarter to ";
    private static final String TO = " to ";
    private static final String PAST = " past ";

    private final TimeFactory timeFactory;

    public TalkingClockService(TimeFactory timeFactory) {
        this.timeFactory = timeFactory;
    }

    public String getCurrentTime() {
        LocalTime localTime = timeFactory.getCurrentTime();
        int hour = localTime.getHour();
        int min = localTime.getMinute();
        return convertTimeIntoHumanReadableFormat(hour, min);
    }

    public String convertTimeIntoHumanReadableFormat(int hour, int minutes){
        validateHourAndMinutes(hour, minutes);
        if(minutes == 0){
            return HOUR_MIN_WORDS[hour] + O_CLOCK;
        }else if( minutes == 15){
            return QUARTER_PAST + HOUR_MIN_WORDS[hour];
        } else if( minutes == 30){
            return HALF_PAST + HOUR_MIN_WORDS[hour];
        } else if( minutes == 45){
            return QUARTER_TO +  HOUR_MIN_WORDS[(hour % 12) + 1];
        } else if (minutes > 30){
            return  HOUR_MIN_WORDS[60 - minutes] + TO + HOUR_MIN_WORDS[(hour % 12) + 1];
        }else {
            return  HOUR_MIN_WORDS[minutes] + PAST + HOUR_MIN_WORDS[hour];
        }
    }

    private void validateHourAndMinutes(int hour, int minutes){
        if(hour > 24 || hour < 0)
            throw new IllegalArgumentException("Invalid parameter Hour : " + hour);
        if(minutes > 60 || minutes < 0)
            throw new IllegalArgumentException("Invalid parameter Minutes : " + minutes);
    }
}
