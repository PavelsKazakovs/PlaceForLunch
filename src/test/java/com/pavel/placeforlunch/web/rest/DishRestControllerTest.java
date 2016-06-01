package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.DishTestData;
import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.service.DishService;
import com.pavel.placeforlunch.util.TestUtil;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.pavel.placeforlunch.DishTestData.*;
import static com.pavel.placeforlunch.UserTestData.ADMIN;
import static com.pavel.placeforlunch.UserTestData.USER2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishRestControllerTest extends RestControllerTest {
    private static final String BASE_URI = "/rest/v1/poll/restaurants/2/dishes";

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private DishService dishService;

    @Test
    public void getAllForRestaurantReturnsAllDishes() throws Exception {
        mockMvc.perform(get(BASE_URI).with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(RESTAURANT2_DISHES));
    }

    @Test
    public void getReturnsCorrectDish() throws Exception {
        mockMvc.perform(get(BASE_URI + "/" + RESTAURANT2_DISH_ID)
                .with(TestUtil.userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentMatcher(DishTestData.RESTAURANT2_DISH));
    }

    @Test
    public void getNotFoundReturns404() throws Exception {
        mockMvc.perform(get(BASE_URI + "/" + DISH_NOT_EXIST.getId())
                .with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createRequiresAdmin() throws Exception {
        mockMvc.perform(post(BASE_URI)
                .with(TestUtil.userHttpBasic(USER2))
                .content(JsonUtil.writeValue(DishTestData.getCreated()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWorksForAdminReturnsLocation() throws Exception {
        Dish newDish = getCreated();
        MvcResult result = mockMvc.perform(post(BASE_URI)
                .with(TestUtil.userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(newDish))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("location");
        //noinspection UnnecessaryLocalVariable
        Dish expected = newDish;
        Integer expectedId = ALL_DISHES.size() + 1;
        expected.setId(expectedId);

        Assert.assertTrue(location.endsWith(BASE_URI + "/" + expectedId));
        Dish actual = JsonUtil.readValue(result.getResponse()
                .getContentAsString(), Dish.class);
        MATCHER.assertEquals(expected, actual);
    }


    @Test
    public void deleteWorksForAdmin() throws Exception {
        mockMvc.perform(delete(BASE_URI + "/" + RESTAURANT2_DISH_ID)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertCollectionEquals(dishService.getByRestaurantId(RESTAURANT2_ID),
                RESTAURANT2_DISHES_WITHOUT_ONE);
    }

    @Test
    public void updateWorksForAdmin() throws Exception {
        mockMvc.perform(put(BASE_URI + "/" + RESTAURANT2_DISH_ID)
                .with(TestUtil.userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(RESTAURANT2_DISH_UPDATED))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        Dish expected = RESTAURANT2_DISH_UPDATED;
        Dish actual = dishService.get(RESTAURANT2_DISH_ID, RESTAURANT2_ID);
        MATCHER.assertEquals(expected, actual);
    }
}
