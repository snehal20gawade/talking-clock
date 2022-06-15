package com.talkingclock.controller;
import com.talkingclock.service.TalkingClockService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class TalkingClockController {

    private static final String TIME_TOKEN = ":";
    private final TalkingClockService talkingClockService;

    public TalkingClockController(TalkingClockService talkingClockService) {
        this.talkingClockService = talkingClockService;
    }

    @GetMapping(path = "/human-readable-time", produces="application/json")
    public String getHumanReadableTime(@RequestParam(name = "time") Optional<String> numericTime){

        if(numericTime.isPresent()) {
            String[] parsedTime = getHourAndMinutesFromTime(numericTime.get());
            int hour = Integer.parseInt(parsedTime[0]);
            int minutes = Integer.parseInt(parsedTime[1]);
            return talkingClockService.convertTimeIntoHumanReadableFormat(hour, minutes);
        }
        return talkingClockService.getCurrentTime();
    }

    private String[] getHourAndMinutesFromTime(String numericTime){
       return numericTime.split(TIME_TOKEN);
    }

}
