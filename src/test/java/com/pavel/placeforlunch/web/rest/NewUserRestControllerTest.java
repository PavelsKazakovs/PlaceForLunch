package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.UserTestData;
import com.pavel.placeforlunch.model.Role;
import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.web.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.EnumSet;

import static com.pavel.placeforlunch.UserTestData.MATCHER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NewUserRestControllerTest extends RestControllerTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserService userService;

    @Test
    public void createNotAcceptingRoles() throws Exception {
        User newUser = UserTestData.getCreatedWithAdminRole();
        mockMvc.perform(post("/rest/v1/new-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createWorksWithoutAuth() throws Exception {
        User newUser = UserTestData.getCreatedNoRole();
        MvcResult result = mockMvc.perform(post("/rest/v1/new-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("location");
        Assert.assertTrue(location.endsWith("/rest/v1/profile"));
        System.out.println("@@@@ DEBUG: " + userService.getAll());
        //noinspection UnnecessaryLocalVariable
        User expected = newUser;

        User actual = userService.getByUsername(expected.getUsername());
        expected.setId(actual.getId());
        expected.setRoles(EnumSet.of(Role.ROLE_USER));
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void usernameValidationNoSpacesAllowed() throws Exception {
        User newUser = UserTestData.getCreatedNoRoleBadUsername();
        mockMvc.perform(post("/rest/v1/new-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }
}
