package com.talkingclock.validator;

import com.talkingclock.exception.NumericTimeFormatException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class NumericTimeValidator {

    //regex for numeric time format '[0-23]:[0-60]'
    //hour – from 0 to 23 , minute – from 0 to 59 seperated by colon ':'
    public static final String VALID_NUMERIC_TIME_FORMAT = "([0-9]|[1][0-9]|2[0-3]):([0-9]|[12345][0-9])";
    final Pattern pattern = Pattern.compile(VALID_NUMERIC_TIME_FORMAT, Pattern.CASE_INSENSITIVE);

    public void validate(String numericTime){
             if(!pattern.matcher(numericTime).matches()){
                 throw new NumericTimeFormatException("Not a valid numeric time : " + numericTime);
             }
        }
}
