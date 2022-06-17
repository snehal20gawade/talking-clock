package com.talkingclock.command;


import com.talkingclock.service.TalkingClockService;
import com.talkingclock.service.TimeFactory;
import com.talkingclock.validator.NumericTimeValidator;

public class TalkingClockCommand {

    public static void main(String[] arg){
        TimeFactory timeFactory = new TimeFactory();
        NumericTimeValidator numericTimeValidator =  new NumericTimeValidator();
        TalkingClockService talkingClockService =  new TalkingClockService(timeFactory, numericTimeValidator);
        if(arg.length == 0) {
            System.out.println(talkingClockService.getHumanFriendlyCurrentTime());
        }else {
            System.out.println(talkingClockService.getHumanFriendlyTime(arg[0]));
        }
    }
}
