package com.pavel.placeforlunch.web.security;

import com.pavel.placeforlunch.model.Role;
import org.springframework.security.core.Authentication;

public class WebSecurity {
    public static boolean ownerOrAdmin(Authentication authentication, String username) {
        return authentication.getName().equals(username) ||
                authentication.getAuthorities().contains(Role.ROLE_ADMIN);
    }
}
