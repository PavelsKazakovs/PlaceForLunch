package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service for managing dish data. Used by admin.
 */
public interface DishService {

    List<Dish> getByRestaurantId(int restaurantId) throws ResourceNotFoundException;

    Dish get(int id, int restaurantId) throws ResourceNotFoundException;

    Dish getWithRestaurant(int id) throws ResourceNotFoundException;

    void delete(int id) throws ResourceNotFoundException;

    /**
     * Delete dish with check if dish belongs to restaurant. If dish does not belong
     * to specified restaurant, {@link ResourceNotFoundException} will be thrown.
     *
     * @param id           dish id
     * @param restaurantId restaurant id
     * @throws ResourceNotFoundException
     */
    void delete(int id, int restaurantId) throws ResourceNotFoundException;

    Dish update(Dish dish, int restaurantId) throws ResourceNotFoundException;

    Dish save(Dish dish, int restaurantId);
}
