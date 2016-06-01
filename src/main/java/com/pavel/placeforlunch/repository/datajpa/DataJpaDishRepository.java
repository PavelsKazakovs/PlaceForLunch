package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    @Autowired
    private ProxyDishRepository proxy;

    @Override
    public List<Dish> getByRestaurantId(int restaurantId) {
        return proxy.getByRestaurantId(restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return proxy.findByIdAndRestaurantId(id, restaurantId);
    }

    @Override
    public boolean delete(int id) {
        return proxy.delete(id) != 0;
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return proxy.deleteCheckRestaurant(id, restaurantId) != 0;
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        return proxy.save(dish);
    }

    @Override
    public Dish getWithRestaurant(int id) {
        return proxy.getWithRestaurant(id);
    }
}
