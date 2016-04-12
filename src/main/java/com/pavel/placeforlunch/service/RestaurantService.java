package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.util.exception.NotFoundException;

import java.util.List;

/**
 * Service for managing restaurant data. Used by admin.
 */
public interface RestaurantService {

    List<Restaurant> getAll();

    /**
     * Get all restaurants and fetch dishes (both offered and
     * not offered) using a single query.
     * @return List of restaurants with all dishes
     */
    List<Restaurant> getAllWithDishes();

    Restaurant get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Restaurant update(Restaurant restaurant) throws NotFoundException;

    Restaurant save(Restaurant restaurant);
}
