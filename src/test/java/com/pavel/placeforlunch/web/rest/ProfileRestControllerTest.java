package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.util.TestUtil;
import com.pavel.placeforlunch.util.UserUtil;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.pavel.placeforlunch.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerTest extends RestControllerTest {

    private static final String BASE_URI = "/rest/v1/profile";
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserService userService;

    @Test
    public void getReturnsProfile() throws Exception {
        mockMvc.perform(get(BASE_URI).with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER2));
    }

    @Test
    public void getRequiresAuthentication() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateWorksAndIgnoresRolesAndIdAndUsername() throws Exception {
        mockMvc.perform(put(BASE_URI).with(TestUtil.userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UserUtil.asTO(USER2_UPDATED_ID_NAME_USERNAME_PW_ROLES))))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertEquals(USER2_UPDATED_NAME_PW, userService.get(USER2.getId()));
    }

    @Test
    public void updateRequiresAuthentication() throws Exception {
        mockMvc.perform(put(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UserUtil.asTO(USER2_UPDATED_ID_NAME_USERNAME_PW_ROLES))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        MATCHER.assertEquals(USER2, userService.get(USER2.getId()));
    }

    @Test
    public void deleteWorks() throws Exception {
        mockMvc.perform(delete(BASE_URI).with(TestUtil.userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertCollectionEquals(Arrays.asList(
                USER1, USER3, USER4, USER5, USER6, USER7, USER8, USER9, USER10), userService.getAll());
    }

    @Test
    public void deleteRequiresAuthentication() throws Exception {
        mockMvc.perform(delete(BASE_URI))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        MATCHER.assertCollectionEquals(ALL_USERS, userService.getAll());
    }
}
