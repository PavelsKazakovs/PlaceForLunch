package com.pavel.placeforlunch.web.json;

public class Views {
    /**
     * Basic view without exposing details.
     */
    public static class Basic {
    }

    public static class WithDishes extends Basic {
    }

    public static class WithRestaurant extends Basic {
    }

    /**
     * Only restaurant ID, no details.
     */
    public static class OnlyRestaurantId extends Basic {
    }

}
