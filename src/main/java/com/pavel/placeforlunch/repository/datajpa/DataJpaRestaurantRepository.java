package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    @Autowired
    private ProxyRestaurantRepository proxy;

    @Override
    public Restaurant get(int id) {
        return proxy.findOne(id);
    }

    @Override
    public Restaurant getFetchDishes(int id) {
        return proxy.getFetchDishes(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return proxy.findAll();
    }

    @Override
    public List<Restaurant> getAllFetchDishes() {
        return proxy.getAllFetchDishes();
    }

    @Override
    public boolean delete(int id) {
        return proxy.delete(id) != 0;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) {
            return null;
        }
        return proxy.save(restaurant);
    }
}
