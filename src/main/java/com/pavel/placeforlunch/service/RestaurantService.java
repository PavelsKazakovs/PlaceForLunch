package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service for managing restaurant data. Used by admin.
 */
public interface RestaurantService {

    List<Restaurant> getAll();

    /**
     * Get all restaurants and fetch dishes using a single query.
     *
     * @return List of restaurants with all dishes
     */
    List<Restaurant> getAllFetchDishes();

    Restaurant get(int id) throws ResourceNotFoundException;

    Restaurant getFetchDishes(int id) throws ResourceNotFoundException;

    void delete(int id) throws ResourceNotFoundException;

    Restaurant update(Restaurant restaurant) throws ResourceNotFoundException;

    Restaurant save(Restaurant restaurant);
}
