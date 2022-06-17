package com.talkingclock.validator;

import com.talkingclock.exception.NumericTimeFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumericTimeValidatorTest {

    private static final String NUMERIC_TIME_WITHOUT_COLON = "130";
    private static final String HOUR_MORE_THAN_TWENTY_THREE = "25:30";
    private static final String MINUTES_MORE_THAN_TWENTY_THREE = "12:61";
    private static final String HOURS_IN_NEGATIVE = "-1:50";
    private static final String MINUTES_IN_NEGATIVE = "1:-45";
    private static final String NUMERIC_TIME_INVALID_FORMAT = "1,45";
    private static final String NOT_VALID_NUMERIC_TIME = "Not a valid numeric time : ";

    private NumericTimeValidator numericTimeValidator;

    @BeforeEach
    public void setUp(){
        numericTimeValidator = new NumericTimeValidator();
    }

    @Test
    void givenValidNumericTime() {
       numericTimeValidator.validate("0:12");
    }

    @Test
    void givenValidNumericTime_Single_Digit_Minutes_StartWithZero() {
        numericTimeValidator.validate("12:01");
    }

    @Test
    void givenValidNumericTime_Single_Digit_Minutes() {
        numericTimeValidator.validate("12:1");
    }

    @Test
    void givenInValidNumericTime_without_colon() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(NUMERIC_TIME_WITHOUT_COLON);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + NUMERIC_TIME_WITHOUT_COLON, exception.getMessage());
    }

    @Test
    void givenInValidNumericTime_Hour_MoreThan_TwentyThree() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(HOUR_MORE_THAN_TWENTY_THREE);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + HOUR_MORE_THAN_TWENTY_THREE, exception.getMessage());
    }

    @Test
    void givenInValidNumericTime_Minutes_MoreThan_FiftyNine() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(MINUTES_MORE_THAN_TWENTY_THREE);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + MINUTES_MORE_THAN_TWENTY_THREE, exception.getMessage());
    }

    @Test
    void givenInValidNumericTime_Negative_Hours() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(HOURS_IN_NEGATIVE);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + HOURS_IN_NEGATIVE, exception.getMessage());
    }

    @Test
    void givenInValidNumericTime_Negative_Minutes() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(MINUTES_IN_NEGATIVE);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + MINUTES_IN_NEGATIVE, exception.getMessage());
    }

    @Test
    void givenInValidNumericTime_Invalid_Format() {
        NumericTimeFormatException exception = assertThrows(NumericTimeFormatException.class, () -> {
            numericTimeValidator.validate(NUMERIC_TIME_INVALID_FORMAT);
        });
        assertEquals(NOT_VALID_NUMERIC_TIME + NUMERIC_TIME_INVALID_FORMAT, exception.getMessage());
    }

}