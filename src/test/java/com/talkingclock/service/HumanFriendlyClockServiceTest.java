package com.talkingclock.service;

import com.talkingclock.exception.NumericTimeFormatException;
import com.talkingclock.validator.NumericTimeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HumanFriendlyClockServiceTest {

    private static final String NUMERIC_TIME_FOUR_O_CLOCK = "4:00";
    private static final String NUMERIC_TIME_HALF_PAST_FOUR = "4:30";
    private static final String INVALID_NUMERIC_TIME = "4:65";
    private static final String HUMAN_FRIENDLY_FOUR_O_CLOCK = "Four o'clock";
    private static final String HUMAN_FRIENDLY_HALF_PAST_FOUR = "Half past Four";

    @Mock
    private TimeFactory timeFactory;
    @Mock
    private NumericTimeValidator numericTimeValidator;

    private HumanFriendlyClockService humanFriendlyClockService;

    @BeforeEach
    public void setUp(){
        humanFriendlyClockService = new HumanFriendlyClockService(timeFactory, numericTimeValidator);
    }

    @Test
    public void currentTimeFourOClock(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 0));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Four o'clock");
    }

    @Test
    public void currentTimeQuarterPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 15));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Quarter past Four");
    }

    @Test
    public void currentTimeHalfPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 30));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Half past Four");
    }

    @Test
    public void currentTimeQuarterToFive(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 45));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Quarter to Five");
    }

    @Test
    public void currentTimeTwentyPastFour(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 20));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Twenty past Four");
    }

    @Test
    public void currentTimeTwentyFiveToFive(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4, 35));
       assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Twenty five to Five");
    }

    @Test
    public void currentTimeTwentyToOne(){
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(0, 40));
        assertThat(humanFriendlyClockService.formattedCurrentTime())
                .isEqualTo("Twenty to One");
    }

    @Test
    void givenNumericTime_Four_O_Clock() {
        String humanFriendlyTime = humanFriendlyClockService.formattedTime(NUMERIC_TIME_FOUR_O_CLOCK);
        assertThat(HUMAN_FRIENDLY_FOUR_O_CLOCK).isEqualTo(humanFriendlyTime);
    }

    @Test
    void givenNumericTime_Half_Past_Four() {
        String humanFriendlyTime = humanFriendlyClockService.formattedTime(NUMERIC_TIME_HALF_PAST_FOUR);
        assertThat(HUMAN_FRIENDLY_HALF_PAST_FOUR).isEqualTo(humanFriendlyTime);
    }

    @Test
    void givenInvalidNumericTime() {
        doThrow(NumericTimeFormatException.class).when(numericTimeValidator).validate(INVALID_NUMERIC_TIME);
        assertThrows(NumericTimeFormatException.class, () -> humanFriendlyClockService.formattedTime(INVALID_NUMERIC_TIME));
    }
}