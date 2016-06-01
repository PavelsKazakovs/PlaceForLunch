package com.pavel.placeforlunch.util;

public enum EntityName {
    DISH("dish"),
    RESTAURANT("restaurant"),
    USER("user"),
    USER_ROLE("user role");

    private String name;

    EntityName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
