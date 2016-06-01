package com.pavel.placeforlunch.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PasswordUtil {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        }
        if (isEncoded(password)) {
            return password;
        }
        return PASSWORD_ENCODER.encode(password);
    }

    public static boolean isEncoded(String password) {
        return BCRYPT_PATTERN.matcher(password).matches();
    }

    public static boolean isMatch(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }
}
