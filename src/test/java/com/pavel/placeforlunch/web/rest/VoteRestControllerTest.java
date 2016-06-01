package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.RestaurantTestData;
import com.pavel.placeforlunch.UserTestData;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.service.VoteService;
import com.pavel.placeforlunch.util.TestUtil;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.pavel.placeforlunch.VoteTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends RestControllerTest {
    private static final String BASE_URI = "/rest/v1/profile/vote";

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private VoteService voteService;

    @Test
    public void getRequiresAuthentication() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getReturnsVoteWithoutRestaurant() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI)
                .with(TestUtil.userHttpBasic(UserTestData.USER_WITH_VOTE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_VOTE_ONLY_RESTAURANTID))
                .andReturn();
        Vote actual = JsonUtil.readValue(result.getResponse()
                .getContentAsString(), Vote.class);
        Assert.assertNull(actual.getRestaurant());
    }

    @Test
    public void getWithRestaurantWorks() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI + "?showRestaurant=true")
                .with(TestUtil.userHttpBasic(UserTestData.USER_WITH_VOTE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_VOTE_WITH_RESTAURANT_NO_USER))
                .andReturn();
        Vote actual = JsonUtil.readValue(result.getResponse().getContentAsString(), Vote.class);
        RestaurantTestData.MATCHER.assertEquals(
                USER_VOTE_WITH_RESTAURANT.getRestaurant(), actual.getRestaurant());
    }

    @Test
    public void voteSavesToDatabaseAndReturnsVoteWithRestaurant() throws Exception {
        mockMvc.perform(put(BASE_URI)
                .with(TestUtil.userHttpBasic(UserTestData.USER_WITH_VOTE))
                .content(JsonUtil.writeValue(USER_VOTE_UPDATED_ONLY_RESTAURANTID))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_VOTE_UPDATED_WITH_RESTAURANT_NO_USER));
        MATCHER.assertEquals(USER_VOTE_UPDATED,
                voteService.getByUserId(UserTestData.USER_WITH_VOTE.getId()));
    }

    @Test
    public void cancelDeletesFromDatabase() throws Exception {
        mockMvc.perform(delete(BASE_URI)
                .with(TestUtil.userHttpBasic(UserTestData.USER_WITH_VOTE)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertEquals(USER_VOTE_DELETED,
                voteService.getByUserId(UserTestData.USER_WITH_VOTE.getId()));
    }
}
