package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.UserTestData;
import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.util.TestUtil;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static com.pavel.placeforlunch.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestControllerTest extends RestControllerTest {

    private static final String BASE_URI = "/rest/v1/admin/users";
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserService userService;

    @Test
    public void getAllWorksForAdmin() throws Exception {
        mockMvc.perform(get(BASE_URI).with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ALL_USERS));
    }

    @Test
    public void getAllNotAvailableToNonAdmin() throws Exception {
        mockMvc.perform(get(BASE_URI).with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createSavesAndReturnsLocation() throws Exception {
        User newUserWithAdminRole = UserTestData.getCreatedWithAdminRole();
        MvcResult result = mockMvc.perform(post(BASE_URI)
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserWithAdminRole)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("location");
        //noinspection UnnecessaryLocalVariable
        User expected = newUserWithAdminRole;

        Assert.assertTrue(location.endsWith(BASE_URI + "/" + expected.getUsername()));
        User actual = userService.getByUsername(expected.getUsername());
        expected.setId(actual.getId());
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void getReturnsCorrectUser() throws Exception {
        mockMvc.perform(get(BASE_URI + "/" + USER2.getUsername())
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER2));
    }

    @Test
    public void getNotFoundReturns404() throws Exception {
        mockMvc.perform(get(BASE_URI + "/" + USER_NOT_EXIST.getUsername())
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWorksOnAllFieldsExceptId() throws Exception {
        mockMvc.perform(put(BASE_URI + "/" + USER2.getUsername())
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(USER2_UPDATED_ID_NAME_USERNAME_PW_ROLES)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertEquals(USER2_UPDATED_NAME_PW_ROLES, userService.get(USER2.getId()));
    }

    @Test
    public void deleteWorksForAdmin() throws Exception {
        mockMvc.perform(delete(BASE_URI + "/" + USER2.getUsername())
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertCollectionEquals(Arrays.asList(
                USER1, USER3, USER4, USER5, USER6, USER7, USER8, USER9, USER10), userService.getAll());
    }
}
