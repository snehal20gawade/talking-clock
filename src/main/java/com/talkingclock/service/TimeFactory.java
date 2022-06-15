package com.talkingclock.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TimeFactory {

    public LocalTime getCurrentTime(){
        return LocalTime.now();
    }
}
