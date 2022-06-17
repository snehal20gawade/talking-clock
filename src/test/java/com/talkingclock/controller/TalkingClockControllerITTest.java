package com.talkingclock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkingclock.exception.NumericTimeFormatException;
import com.talkingclock.service.TalkingClockService;
import com.talkingclock.service.TimeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TalkingClockController.class)
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
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    TimeFactory timeFactory;

    @MockBean
    TalkingClockService talkingClockService;

    @Test
    public void timeIsNotProvidedShouldReturnCurrentTime() throws Exception {
        when(talkingClockService.getHumanFriendlyCurrentTime()).thenReturn(HUMAN_FRIENDLY_FOUR_O_CLOCK);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_CURRENT_TIME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(HUMAN_FRIENDLY_FOUR_O_CLOCK));
    }

    @Test
    public void givenTime_Two_O_Clock() throws Exception {
        when(talkingClockService.getHumanFriendlyTime(NUMERIC_TIME_TWO_O_CLOCK)).thenReturn(HUMAN_FRIENDLY_TWO_O_CLOCK);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_TIME_WITH_PARAMETER + NUMERIC_TIME_TWO_O_CLOCK))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(HUMAN_FRIENDLY_TWO_O_CLOCK));
    }

    @Test
    public void givenTime_InvalidFormat() throws Exception {
        doThrow(new NumericTimeFormatException(NOT_VALID_NUMERIC_TIME + INVALID_NUMERIC_TIME)).when(talkingClockService).getHumanFriendlyTime(INVALID_NUMERIC_TIME);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_TIME_WITH_PARAMETER + INVALID_NUMERIC_TIME))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(NOT_VALID_NUMERIC_TIME + INVALID_NUMERIC_TIME));
    }
}