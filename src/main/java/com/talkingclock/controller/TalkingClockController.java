package com.talkingclock.controller;
import com.talkingclock.exception.NumericTimeFormatException;
import com.talkingclock.service.TalkingClockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api")
public class TalkingClockController {

    private final TalkingClockService talkingClockService;

    public TalkingClockController(TalkingClockService talkingClockService) {
        this.talkingClockService = talkingClockService;
    }

    @GetMapping(path = "/human-readable-time", produces="application/json")
    public ResponseEntity<String> getHumanReadableTime(@RequestParam(name = "time") Optional<String> numericTime){
        if(numericTime.isPresent()) {
            try {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(talkingClockService.getHumanFriendlyTime(numericTime.get()));
            } catch (NumericTimeFormatException numericTimeFormatException){
                return ResponseEntity.badRequest()
                        .body(numericTimeFormatException.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(talkingClockService.getHumanFriendlyCurrentTime());
    }

}
