package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.DishTestData;
import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static com.pavel.placeforlunch.RestaurantTestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantServiceTest extends ServiceTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    protected RestaurantService restaurantService;

    @Test
    public void getAll() throws Exception {
        List<Restaurant> actual = restaurantService.getAll();
        MATCHER.assertCollectionEquals(ALL_RESTAURANTS, actual);
        LOG.debug("expected: " + ALL_RESTAURANTS);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getAllFetchDishes() throws Exception {
        List<Restaurant> actual = restaurantService.getAllFetchDishes();
        Assert.assertEquals(actual.size(), ALL_RESTAURANTS.size());
        MATCHER.assertEquals(RESTAURANT2, actual.get(1));
        List<Dish> dishesFetched = actual.get(1).getDishes();
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.RESTAURANT2_DISHES, dishesFetched);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = restaurantService.get(2);
        MATCHER.assertEquals(RESTAURANT2, actual);
        LOG.debug("expected: " + RESTAURANT2);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getNotFound() throws Exception {
        int id = RESTAURANT_NOT_EXIST.getId();
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found restaurant with id=" + id);
        restaurantService.get(id);
    }

    @Test
    public void getFetchDishes() throws Exception {
        Restaurant actual = restaurantService.getFetchDishes(2);
        MATCHER.assertEquals(RESTAURANT2, actual);
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.RESTAURANT2_DISHES, actual.getDishes());
        LOG.debug("expected: " + RESTAURANT2);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getFetchDishesNotFound() throws Exception {
        int id = RESTAURANT_NOT_EXIST.getId();
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found restaurant with id=" + id);
        restaurantService.getFetchDishes(id);
    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(RESTAURANT2.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT3, RESTAURANT4), restaurantService.getAll());
    }

    @Test
    public void deleteNotFound() throws Exception {
        int id = RESTAURANT_NOT_EXIST.getId();
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found restaurant with id=" + id);
        restaurantService.delete(id);
    }

    @Test
    public void update() throws Exception {
        Restaurant returned = restaurantService.update(RESTAURANT2_UPDATED);
        Restaurant actual = restaurantService.get(2);
        MATCHER.assertEquals(RESTAURANT2_UPDATED, actual);
        MATCHER.assertEquals(RESTAURANT2_UPDATED, returned);
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + RESTAURANT2_UPDATED);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found restaurant with id=" + RESTAURANT_NOT_EXIST.getId()));
        Restaurant returned = restaurantService.update(RESTAURANT_NOT_EXIST);
        LOG.debug("  passed: " + RESTAURANT_NOT_EXIST);
        LOG.debug("returned: " + returned);
    }

    @Test
    public void save() throws Exception {
        Restaurant created = getCreated();
        Restaurant returned = restaurantService.save(created);
        Restaurant actual = restaurantService.get(created.getId());
        MATCHER.assertEquals(created, returned);
        MATCHER.assertEquals(created, actual);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, created), restaurantService.getAll());
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + created);
        LOG.debug("  actual: " + actual);
    }
}
