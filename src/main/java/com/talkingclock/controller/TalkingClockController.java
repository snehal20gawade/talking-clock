package com.talkingclock.controller;
import com.talkingclock.exception.NumericTimeFormatException;
import com.talkingclock.service.ClockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api")
public class TalkingClockController {

    private final ClockService clockService;

    public TalkingClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    @GetMapping(path = "/human-friendly-time", produces="application/json")
    public ResponseEntity<String> getHumanFriendlyTime(@RequestParam(name = "time") Optional<String> numericTime){
        if(numericTime.isPresent()) {
            try {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(clockService.humanFriendlyTime(numericTime.get()));
            } catch (NumericTimeFormatException numericTimeFormatException){
                return ResponseEntity.badRequest()
                        .body(numericTimeFormatException.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(clockService.humanFriendlyCurrentTime());
    }

}
