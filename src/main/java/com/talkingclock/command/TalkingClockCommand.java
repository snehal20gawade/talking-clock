package com.talkingclock.command;


import com.talkingclock.controller.TalkingClockController;
import com.talkingclock.service.TalkingClockService;
import com.talkingclock.service.TimeFactory;

import java.util.Optional;

public class TalkingClockCommand {

    public static void main(String[] arg){
        TimeFactory timeFactory = new TimeFactory();
        TalkingClockService talkingClockService =  new TalkingClockService(timeFactory);
        TalkingClockController talkingClockController =  new TalkingClockController(talkingClockService);
        if(arg.length == 0) {
            System.out.println(talkingClockController.getHumanReadableTime(Optional.empty()));
        }else {
            System.out.println(talkingClockController.getHumanReadableTime(Optional.of(arg[0])));
        }
    }
}
