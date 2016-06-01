package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.repository.DishRepository;
import com.pavel.placeforlunch.repository.RestaurantRepository;
import com.pavel.placeforlunch.util.EntityName;
import com.pavel.placeforlunch.util.exception.ExceptionUtil;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * @param restaurantId restaurant ID
     * @return list of dishes for restaurant
     * @throws ResourceNotFoundException if restaurant was not found
     */
    @Override
    @Transactional(readOnly = true)
    public List<Dish> getByRestaurantId(int restaurantId) throws ResourceNotFoundException {
        List<Dish> dishes = repository.getByRestaurantId(restaurantId);
        if (dishes.isEmpty()) {
            ExceptionUtil.check(restaurantRepository.get(restaurantId), EntityName.RESTAURANT, restaurantId);
        }
        return dishes;
    }

    @Override
    @Transactional(readOnly = true)
    public Dish get(int id, int restaurantId) throws ResourceNotFoundException {
        Dish dish = repository.get(id, restaurantId);
        if (dish == null) {
            ExceptionUtil.check(restaurantRepository.get(restaurantId), EntityName.RESTAURANT, restaurantId);
        }
        return ExceptionUtil.check(dish, EntityName.DISH, id, EntityName.RESTAURANT, restaurantId);
    }

    @Override
    public Dish getWithRestaurant(int id) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.getWithRestaurant(id), EntityName.DISH, id);
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        ExceptionUtil.check(repository.delete(id), EntityName.DISH, id);
    }

    @Override
    public void delete(int id, int restaurantId) throws ResourceNotFoundException {
        ExceptionUtil.check(repository.delete(id, restaurantId),
                EntityName.DISH, id, EntityName.RESTAURANT, restaurantId);
    }

    @Override
    @Transactional
    public Dish update(Dish dish, int restaurantId) throws ResourceNotFoundException {
        Restaurant restaurant = ExceptionUtil.check(restaurantRepository.get(restaurantId), EntityName.RESTAURANT, restaurantId);
        dish.setRestaurant(restaurant);
        return ExceptionUtil.check(repository.save(dish, restaurantId), EntityName.DISH, dish.getId(), EntityName.RESTAURANT, restaurantId);
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        Restaurant restaurant = ExceptionUtil.check(restaurantRepository.get(restaurantId), EntityName.RESTAURANT, restaurantId);
        dish.setRestaurant(restaurant);
        return repository.save(dish, restaurantId);
    }
}
