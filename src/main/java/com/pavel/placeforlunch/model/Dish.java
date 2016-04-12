package com.pavel.placeforlunch.model;

public class Dish extends NamedEntity {

    private int price;
    private boolean isOffered;
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOffered() {
        return isOffered;
    }

    public void setOffered(boolean offered) {
        isOffered = offered;
    }
}
