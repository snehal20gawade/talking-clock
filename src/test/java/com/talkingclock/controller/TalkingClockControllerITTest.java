package com.talkingclock.controller;

import com.talkingclock.service.TimeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TalkingClockControllerITTest {

    private static final String HUMAN_FRIENDLY_FOUR_O_CLOCK = "Four o'clock";
    private static final String HUMAN_FRIENDLY_TWO_O_CLOCK = "Two o'clock";
    private static final String API_HUMAN_READABLE_CURRENT_TIME = "/api/human-readable-time";
    private static final String NOT_VALID_NUMERIC_TIME = "Not a valid numeric time : ";
    private static final String INVALID_NUMERIC_TIME = "25:0";
    private static final String API_HUMAN_READABLE_TIME_WITH_PARAMETER = "/api/human-readable-time?time=";
    private static final String NUMERIC_TIME_TWO_O_CLOCK = "2:0";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TimeFactory timeFactory;

    @Test
    public void timeIsNotProvidedShouldReturnCurrentTime() throws Exception {
        when(timeFactory.getCurrentTime()).thenReturn(LocalTime.of(4,0));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_CURRENT_TIME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(HUMAN_FRIENDLY_FOUR_O_CLOCK));
    }

    @Test
    public void givenTime_Two_O_Clock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_TIME_WITH_PARAMETER + NUMERIC_TIME_TWO_O_CLOCK))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(HUMAN_FRIENDLY_TWO_O_CLOCK));
    }

    @Test
    public void givenTime_InvalidFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_TIME_WITH_PARAMETER + INVALID_NUMERIC_TIME))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(NOT_VALID_NUMERIC_TIME + INVALID_NUMERIC_TIME));
    }
}