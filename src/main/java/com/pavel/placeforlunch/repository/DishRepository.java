package com.pavel.placeforlunch.repository;

import com.pavel.placeforlunch.model.Dish;

import java.util.List;

public interface DishRepository {
    List<Dish> getByRestaurantId(int restaurantId);

    Dish get(int id, int restaurantId);

    boolean delete(int id);

    boolean delete(int id, int restaurantId);

    Dish save(Dish dish, int restaurantId);

    Dish getWithRestaurant(int id);
}
