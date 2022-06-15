package com.talkingclock.controller;

import com.talkingclock.service.TalkingClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TalkingClockControllerTest {

    private static final String FOUR_O_CLOCK = "Four o'clock";
    private static final String HALF_PAST_FOUR = "Half past four";
    private static final String THREE_O_CLOCK = "Three o'clock";
    private static final String NUMERIC_TIME_FOUR_O_CLOCK = "4:00";
    private static final String NUMERIC_TIME_HALF_PAST_FOUR = "4:30";

    @Mock
    private TalkingClockService talkingClockService;

    private TalkingClockController talkingClockController;

    @BeforeEach
    void setUp() {
        talkingClockController = new TalkingClockController(talkingClockService);
    }

    @Test
    void givenNumericTime_Four_O_Clock() {
        when(talkingClockService.convertTimeIntoHumanReadableFormat(4, 0)).thenReturn(FOUR_O_CLOCK);
        String humanReadableTime = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_FOUR_O_CLOCK));
        assertThat(FOUR_O_CLOCK).isEqualTo(humanReadableTime);
    }

    @Test
    void givenNumericTime_Half_Past_Four() {
        when(talkingClockService.convertTimeIntoHumanReadableFormat(4, 30)).thenReturn(HALF_PAST_FOUR);
        String humanReadableTime = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_HALF_PAST_FOUR));
        assertThat(HALF_PAST_FOUR).isEqualTo(humanReadableTime);
    }

    @Test
    void givenNumericTimeNotPresentThenReturnCurrentTime() {
        when(talkingClockService.getCurrentTime()).thenReturn(THREE_O_CLOCK);
        String humanReadableTime = talkingClockController.getHumanReadableTime(Optional.empty());
        assertThat(THREE_O_CLOCK).isEqualTo(humanReadableTime);
    }
}