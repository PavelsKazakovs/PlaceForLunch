package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.DishTestData;
import com.pavel.placeforlunch.RestaurantTestData;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.service.RestaurantService;
import com.pavel.placeforlunch.util.TestUtil;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static com.pavel.placeforlunch.RestaurantTestData.*;
import static com.pavel.placeforlunch.UserTestData.ADMIN;
import static com.pavel.placeforlunch.UserTestData.USER2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends RestControllerTest {

    private static final String BASE_URI = "/rest/v1/poll/restaurants";

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void getAllFetchDishesWorks() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI + "?showDishes=true")
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ALL_RESTAURANTS))
                .andReturn();
        List<Restaurant> actual = JsonUtil.readValues(
                result.getResponse().getContentAsString(), Restaurant.class);
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.RESTAURANT2_DISHES,
                actual.get(1).getDishes());
    }

    @Test
    public void getAllReturnsNoDishes() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI)
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ALL_RESTAURANTS))
                .andReturn();
        List<Restaurant> actual = JsonUtil.readValues(result.getResponse()
                .getContentAsString(), Restaurant.class);
        Assert.assertNull(actual.get(1).getDishes());
    }

    @Test
    public void getFetchDishesWorks() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI + "/" +
                RESTAURANT2.getId() + "?showDishes=true")
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(RESTAURANT2))
                .andReturn();
        Restaurant actual = JsonUtil.readValue(result.getResponse().getContentAsString(),
                Restaurant.class);
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.RESTAURANT2_DISHES,
                actual.getDishes());
    }

    @Test
    public void getReturnsNoDishes() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URI + "/" + RESTAURANT2.getId())
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(RESTAURANT2))
                .andReturn();
        Restaurant actual = JsonUtil.readValue(result.getResponse()
                .getContentAsString(), Restaurant.class);
        Assert.assertNull(actual.getDishes());
    }

    @Test
    public void getNotFoundReturns404() throws Exception {
        mockMvc.perform(get(BASE_URI + "/" + RESTAURANT_NOT_EXIST.getId())
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void createRequiresAdmin() throws Exception {
        mockMvc.perform(post(BASE_URI)
                .with(TestUtil.userHttpBasic(USER2))
                .content(JsonUtil.writeValue(RestaurantTestData.getCreated()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWorksForAdminReturnsLocation() throws Exception {
        Restaurant newRestaurant = getCreated();
        MvcResult result = mockMvc.perform(post(BASE_URI)
                .with(TestUtil.userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("location");
        //noinspection UnnecessaryLocalVariable
        Restaurant expected = newRestaurant;
        Integer expectedId = ALL_RESTAURANTS.size() + 1;
        expected.setId(expectedId);

        Assert.assertTrue(location.endsWith("/poll/restaurants/" + expectedId));
        Restaurant actual = JsonUtil.readValue(result.getResponse()
                .getContentAsString(), Restaurant.class);
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void deleteWorksForAdmin() throws Exception {
        mockMvc.perform(delete(BASE_URI + "/" + RESTAURANT2.getId())
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertCollectionEquals(restaurantService.getAll(),
                Arrays.asList(RESTAURANT1, RESTAURANT3, RESTAURANT4));
    }

    @Test
    public void updateWorksForAdmin() throws Exception {
        mockMvc.perform(put(BASE_URI + "/" + RESTAURANT2.getId())
                .with(TestUtil.userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(RESTAURANT2_UPDATED))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        Restaurant expected = RESTAURANT2_UPDATED;
        Restaurant actual = restaurantService.get(RESTAURANT2.getId());
        MATCHER.assertEquals(expected, actual);
    }

}
