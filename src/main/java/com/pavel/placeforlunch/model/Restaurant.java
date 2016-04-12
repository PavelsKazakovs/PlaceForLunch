package com.pavel.placeforlunch.model;

import java.util.List;

public class Restaurant extends NamedEntity {

    private List<Dish> dishes;
    private List<Dish> todaysOffer;
    private boolean isActive;
}
