package com.talkingclock.controller;

import com.talkingclock.exception.NumericTimeFormatException;
import com.talkingclock.service.ClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TalkingClockControllerTest {

    private static final String HUMAN_FRIENDLY_FOUR_O_CLOCK = "Four o'clock";
    private static final String HUMAN_FRIENDLY_HALF_PAST_FOUR = "Half past four";
    private static final String HUMAN_FRIENDLY_THREE_O_CLOCK = "Three o'clock";
    private static final String NUMERIC_TIME_FOUR_O_CLOCK = "4:00";
    private static final String NUMERIC_TIME_HALF_PAST_FOUR = "4:30";
    private static final String INVALID_NUMERIC_TIME = "4:65";
    private static final String NOT_VALID_NUMERIC_TIME = "Not a valid numeric time : ";

    @Mock
    private ClockService clockService;

    private TalkingClockController talkingClockController;

    @BeforeEach
    void setUp() {
        talkingClockController = new TalkingClockController(clockService);
    }

    @Test
    void givenNumericTime_Four_O_Clock() {
        when(clockService.formattedTime(NUMERIC_TIME_FOUR_O_CLOCK)).thenReturn(HUMAN_FRIENDLY_FOUR_O_CLOCK);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_FOUR_O_CLOCK));
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_FOUR_O_CLOCK).isEqualTo(response.getBody());
    }

    @Test
    void givenNumericTime_Half_Past_Four() {
        when(clockService.formattedTime(NUMERIC_TIME_HALF_PAST_FOUR)).thenReturn(HUMAN_FRIENDLY_HALF_PAST_FOUR);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.of(NUMERIC_TIME_HALF_PAST_FOUR));
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_HALF_PAST_FOUR).isEqualTo(response.getBody());
    }

    @Test
    void givenNumericTimeNotPresentThenReturnCurrentTime() {
        when(clockService.formattedCurrentTime()).thenReturn(HUMAN_FRIENDLY_THREE_O_CLOCK);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.empty());
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertThat(HUMAN_FRIENDLY_THREE_O_CLOCK).isEqualTo(response.getBody());
    }

    @Test
    void givenInvalidNumericTime() {
        doThrow(new NumericTimeFormatException(NOT_VALID_NUMERIC_TIME + INVALID_NUMERIC_TIME)).when(clockService).formattedTime(INVALID_NUMERIC_TIME);
        ResponseEntity<String> response = talkingClockController.getHumanReadableTime(Optional.of(INVALID_NUMERIC_TIME));
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
        assertThat(NOT_VALID_NUMERIC_TIME + INVALID_NUMERIC_TIME).isEqualTo(response.getBody());
    }
}