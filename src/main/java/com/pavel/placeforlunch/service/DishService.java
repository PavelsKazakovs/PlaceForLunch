package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.util.exception.NotFoundException;

import java.util.List;

/**
 * Service for managing dish data. Used by admin.
 */
public interface DishService {

    List<Dish> getByRestaurantId(int restaurantId) throws NotFoundException;

    Dish get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Dish update(Dish dish) throws NotFoundException;

    Dish save(Dish dish);
}
