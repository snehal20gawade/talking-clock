package com.talkingclock.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TalkingClockServiceTest {

    @Mock
    private TimeFactory timeFactory;

    private TalkingClockService talkingClockService;

    @BeforeEach
    public void setUp(){
        talkingClockService = new TalkingClockService(timeFactory);
    }

    @Test
    public void currentTimeFourOClock(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 0));
        assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Four o'clock");
    }

    @Test
    public void currentTimeQuarterPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 15));
        assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Quarter past Four");
    }

    @Test
    public void currentTimeHalfPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 30));
        assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Half past Four");
    }

    @Test
    public void currentTimeQuarterToFive(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 45));
        assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Quarter to Five");
    }

    @Test
    public void currentTimeTwentyPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 20));
        assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Twenty past Four");
    }

    @Test
    public void currentTimeTwentyFiveToFive(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 35));
       assertThat(talkingClockService.getCurrentTime())
                .isEqualTo("Twenty five to Five");
    }

    @Test
    public void givenInvalidHourMoreThanTwentyFour(){
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            talkingClockService.convertTimeIntoHumanReadableFormat(30, 0);
        });

        Assertions.assertEquals("Invalid parameter Hour : 30", exception.getMessage());
    }

    @Test
    public void givenHoursInNegative(){
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            talkingClockService.convertTimeIntoHumanReadableFormat(-1, 0);
        });

        Assertions.assertEquals("Invalid parameter Hour : -1", exception.getMessage());
    }

    @Test
    public void givenInvalidMinutesMoreThanSixty(){
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            talkingClockService.convertTimeIntoHumanReadableFormat(1, 80);
        });

        Assertions.assertEquals("Invalid parameter Minutes : 80", exception.getMessage());
    }

    @Test
    public void givenMinutesInNegative(){
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            talkingClockService.convertTimeIntoHumanReadableFormat(1, -1);
        });

        Assertions.assertEquals("Invalid parameter Minutes : -1", exception.getMessage());
    }
}