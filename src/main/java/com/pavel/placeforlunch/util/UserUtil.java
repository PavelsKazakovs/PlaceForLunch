package com.pavel.placeforlunch.util;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.to.UserTO;

/**
 * Transfer object for transferring user data from API to server.
 */
public class UserUtil {
    public static User updateFromTO(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setPassword(userTO.getPassword());
        return prepareToSave(user);
    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        return user;
    }

    public static UserTO asTO(User user) {
        return new UserTO(user.getId(), user.getName(), user.getUsername(),
                user.getPassword());
    }
}
