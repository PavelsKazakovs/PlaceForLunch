package com.pavel.placeforlunch.repository;

import com.pavel.placeforlunch.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    Restaurant getFetchDishes(int id);

    List<Restaurant> getAll();

    List<Restaurant> getAllFetchDishes();

    boolean delete(int id);

    Restaurant save(Restaurant restaurant);
}
