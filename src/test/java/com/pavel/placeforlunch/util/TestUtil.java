package com.pavel.placeforlunch.util;

import com.pavel.placeforlunch.model.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;

public class TestUtil {

    public static ResultActions print(ResultActions actions) throws UnsupportedEncodingException {
        System.out.println(getContent(actions));
        return actions;
    }

    static String getContent(ResultActions actions) throws UnsupportedEncodingException {
        return actions.andReturn().getResponse().getContentAsString();
    }

    public static class ToStringMatcher<T> extends ModelMatcher<T, String> {
        public ToStringMatcher(Class<T> entityClass) {
            super(ObjectUtils::nullSafeToString, entityClass);
        }
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getUsername(), user.getPassword());
    }
}
