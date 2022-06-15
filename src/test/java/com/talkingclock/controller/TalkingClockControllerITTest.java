package com.talkingclock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkingclock.service.TalkingClockService;
import com.talkingclock.service.TimeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TalkingClockController.class)
public class TalkingClockControllerITTest {

    private static final String FOUR_O_CLOCK = "Four o'clock";
    private static final String TWO_O_CLOCK = "Two o'clock";
    public static final String API_HUMAN_READABLE_CURRENT_TIME = "/api/human-readable-time";
    public static final String API_HUMAN_READABLE_TIME = "/api/human-readable-time?";

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
        when(talkingClockService.getCurrentTime()).thenReturn(FOUR_O_CLOCK);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_HUMAN_READABLE_CURRENT_TIME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(FOUR_O_CLOCK));
    }

    @Test
    public void givenTime_Two_O_Clock() throws Exception {
        when(talkingClockService.convertTimeIntoHumanReadableFormat(2, 0)).thenReturn(TWO_O_CLOCK);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/human-readable-time?time=2:0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TWO_O_CLOCK));
    }
}