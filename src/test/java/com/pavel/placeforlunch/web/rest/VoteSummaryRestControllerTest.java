package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.util.TestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.pavel.placeforlunch.RestaurantTestData.*;
import static com.pavel.placeforlunch.UserTestData.USER2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteSummaryRestControllerTest extends RestControllerTest {
    private static final String BASE_URI = "/rest/v1/poll/vote-summary";

    @Test
    public void getSummaryWorks() throws Exception {
        mockMvc.perform(get(BASE_URI)
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_VOTES.contentListMatcher(RESTAURANTS_WITH_VOTES_ONLY_ID))
                .andReturn();
    }

    @Test
    public void getWithRestaurantDetailsWorks() throws Exception {
        mockMvc.perform(get(BASE_URI + "?showRestaurants=true")
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_VOTES.contentListMatcher(RESTAURANTS_WITH_VOTES))
                .andReturn();
    }

}
