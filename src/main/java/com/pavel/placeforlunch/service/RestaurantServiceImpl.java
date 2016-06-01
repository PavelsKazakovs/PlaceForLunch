package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.repository.RestaurantRepository;
import com.pavel.placeforlunch.util.EntityName;
import com.pavel.placeforlunch.util.exception.ExceptionUtil;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Restaurant> getAllFetchDishes() {
        return repository.getAllFetchDishes();
    }

    @Override
    public Restaurant get(int id) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.get(id), EntityName.RESTAURANT, id);
    }

    @Override
    public Restaurant getFetchDishes(int id) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.getFetchDishes(id), EntityName.RESTAURANT, id);
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        ExceptionUtil.check(repository.delete(id), EntityName.RESTAURANT, id);
    }

    @Override
    public Restaurant update(Restaurant restaurant) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.save(restaurant), EntityName.RESTAURANT, restaurant.getId());
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }
}
