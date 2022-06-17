package com.talkingclock.service;

import com.talkingclock.validator.NumericTimeValidator;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class HumanFriendlyClockService implements ClockService {

    private static final String[] HOUR_MIN_HUMAN_FRIENDLY_FORMAT = { "Zero", "One", "Two", "Three", "Four",
            "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen",
            "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen", "Twenty", "Twenty one",
            "Twenty two", "Twenty three", "Twenty four",
            "Twenty five", "Twenty six", "Twenty seven",
            "Twenty eight", "Twenty nine",
    };
    private static final String QUARTER_PAST = "Quarter past ";
    private static final String O_CLOCK = " o'clock";
    private static final String HALF_PAST = "Half past ";
    private static final String QUARTER_TO = "Quarter to ";
    private static final String TO = " to ";
    private static final String PAST = " past ";
    private static final String TIME_TOKEN = ":";

    private final TimeFactory timeFactory;
    private final NumericTimeValidator numericTimeValidator;

    public HumanFriendlyClockService(TimeFactory timeFactory, NumericTimeValidator numericTimeValidator) {
        this.timeFactory = timeFactory;
        this.numericTimeValidator = numericTimeValidator;
    }

    @Override
    public String formattedCurrentTime() {
        LocalTime localTime = timeFactory.getCurrentTime();
        int hour = localTime.getHour();
        int min = localTime.getMinute();
        return convertTimeIntoHumanFriendlyFormat(hour, min);
    }

    @Override
    public String formattedTime(String numericTime){
        String[] parsedTime = getHourAndMinutesFromTime(numericTime);
        int hour = Integer.parseInt(parsedTime[0]);
        int minutes = Integer.parseInt(parsedTime[1]);
        return convertTimeIntoHumanFriendlyFormat(hour, minutes);

    }
    public String convertTimeIntoHumanFriendlyFormat(int hour, int minutes){
        if(minutes == 0){
            return HOUR_MIN_HUMAN_FRIENDLY_FORMAT[hour] + O_CLOCK;
        }else if( minutes == 15){
            return QUARTER_PAST + HOUR_MIN_HUMAN_FRIENDLY_FORMAT[hour];
        } else if( minutes == 30){
            return HALF_PAST + HOUR_MIN_HUMAN_FRIENDLY_FORMAT[hour];
        } else if( minutes == 45){
            return QUARTER_TO +  HOUR_MIN_HUMAN_FRIENDLY_FORMAT[(hour % 12) + 1];
        } else if (minutes > 30){
            return  HOUR_MIN_HUMAN_FRIENDLY_FORMAT[60 - minutes] + TO + HOUR_MIN_HUMAN_FRIENDLY_FORMAT[(hour % 12) + 1];
        }else {
            return  HOUR_MIN_HUMAN_FRIENDLY_FORMAT[minutes] + PAST + HOUR_MIN_HUMAN_FRIENDLY_FORMAT[hour];
        }
    }

    private String[] getHourAndMinutesFromTime(String numericTime){
        numericTimeValidator.validate(numericTime);
        return numericTime.split(TIME_TOKEN);
    }

}
