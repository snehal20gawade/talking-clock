package com.talkingclock.controller;

import com.talkingclock.service.TalkingClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TalkingClockControllerTest {

    private static final String HUMAN_FRIENDLY_FOUR_O_CLOCK = "Four o'clock";
    private static final String HUMAN_FRIENDLY_HALF_PAST_FOUR = "Half past four";
    private static final String HUMAN_FRIENDLY_THREE_O_CLOCK = "Three o'clock";
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
        when(talkingClockService.getHumanFriendlyTime(NUMERIC_TIME_FOUR_O_CLOCK)).thenReturn(HUMAN_FRIENDLY_FOUR_O_CLOCK);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_FOUR_O_CLOCK));
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_FOUR_O_CLOCK).isEqualTo(response.getBody());
    }

    @Test
    void givenNumericTime_Half_Past_Four() {
        when(talkingClockService.getHumanFriendlyTime(NUMERIC_TIME_HALF_PAST_FOUR)).thenReturn(HUMAN_FRIENDLY_HALF_PAST_FOUR);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_HALF_PAST_FOUR));
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_HALF_PAST_FOUR).isEqualTo(response.getBody());
    }

    @Test
    void givenNumericTimeNotPresentThenReturnCurrentTime() {
        when(talkingClockService.getHumanFriendlyCurrentTime()).thenReturn(HUMAN_FRIENDLY_THREE_O_CLOCK);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.empty());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_THREE_O_CLOCK).isEqualTo(response.getBody());
    }

}