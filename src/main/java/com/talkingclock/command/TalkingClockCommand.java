package com.talkingclock.command;


import com.talkingclock.service.HumanFriendlyClockService;
import com.talkingclock.service.TimeFactory;
import com.talkingclock.validator.NumericTimeValidator;

public class TalkingClockCommand {

    public static void main(String[] arg){
        TimeFactory timeFactory = new TimeFactory();
        NumericTimeValidator numericTimeValidator =  new NumericTimeValidator();
        HumanFriendlyClockService humanFriendlyClockService =  new HumanFriendlyClockService(timeFactory, numericTimeValidator);
        if(arg.length == 0) {
            System.out.println(humanFriendlyClockService.formattedCurrentTime());
        }else {
            System.out.println(humanFriendlyClockService.formattedTime(arg[0]));
        }
    }
}
