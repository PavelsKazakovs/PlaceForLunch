package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.DishTestData;
import com.pavel.placeforlunch.RestaurantTestData;
import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static com.pavel.placeforlunch.DishTestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class DishServiceTest extends ServiceTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    protected DishService dishService;

    @Test
    public void getByRestaurantId() throws Exception {
        List<Dish> actual = dishService.getByRestaurantId(2);
        MATCHER.assertCollectionEquals(RESTAURANT2_DISHES, actual);
    }

    @Test
    public void getByRestaurantIdNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found restaurant with id=99"));
        List<Dish> actual = dishService.getByRestaurantId(99);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void get() throws Exception {
        Dish actual = dishService.get(RESTAURANT1_DISH_ID, 1);
        MATCHER.assertEquals(RESTAURANT1_DISH, actual);
        LOG.debug("expected: " + RESTAURANT1_DISH);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found dish with id=" + RESTAURANT2_DISH_ID + " for restaurant with id=1"));
        dishService.get(RESTAURANT2_DISH_ID, 1);
    }

    @Test
    public void getNotFoundRestaurant() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found restaurant with id=99"));
        dishService.get(2, 99);
    }

    @Test
    public void getWithRestaurant() throws Exception {
        Dish actualDish = dishService.getWithRestaurant(1);
        MATCHER.assertEquals(DISH1, actualDish);
        Restaurant actualRestaurant = actualDish.getRestaurant();
        RestaurantTestData.MATCHER.assertEquals(RestaurantTestData.RESTAURANT1, actualRestaurant);

        LOG.debug("expected: " + DISH1);
        LOG.debug("  actual: " + actualDish);
        LOG.debug("expected: " + RestaurantTestData.RESTAURANT1);
        LOG.debug("  actual: " + actualRestaurant);
    }

    @Test
    public void getWithRestaurantNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found dish with id=99"));
        dishService.getWithRestaurant(99);
    }

    @Test
    public void delete() throws Exception {
        MATCHER.assertCollectionEquals(RESTAURANT2_DISHES, dishService.getByRestaurantId(2));
        dishService.delete(2);
        MATCHER.assertCollectionEquals(Arrays.asList(DISH6, DISH9), dishService.getByRestaurantId(2));
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found dish with id=99"));
        dishService.delete(99);
    }

    @Test
    public void deleteCheckRestaurant() throws Exception {
        dishService.delete(2, 2);
        MATCHER.assertCollectionEquals(Arrays.asList(DISH6, DISH9), dishService.getByRestaurantId(2));
    }

    @Test
    public void deleteCheckRestaurantNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found dish with id=1 for restaurant with id=2"));
        dishService.delete(1, 2);
    }

    @Test
    public void update() throws Exception {
        Dish returned = dishService.update(RESTAURANT2_DISH_UPDATED, 2);
        Dish actual = dishService.get(RESTAURANT2_DISH_ID, 2);
        MATCHER.assertEquals(RESTAURANT2_DISH_UPDATED, actual);
        MATCHER.assertEquals(RESTAURANT2_DISH_UPDATED, returned);
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + RESTAURANT2_DISH_UPDATED);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found dish with id=" + DISH_NOT_EXIST.getId() + " for restaurant with id=1"));
        dishService.update(DISH_NOT_EXIST, 1);
    }

    @Test
    public void updateNotFoundRestaurant() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found restaurant with id=99"));
        dishService.update(RESTAURANT2_DISH_UPDATED, 99);
    }

    @Test
    public void save() throws Exception {
        Dish created = DishTestData.getCreated();
        Dish returned = dishService.save(created, 2);
        Dish actual = dishService.get(created.getId(), 2);
        MATCHER.assertEquals(created, returned);
        MATCHER.assertEquals(created, actual);
        MATCHER.assertCollectionEquals(Arrays.asList(DISH2, DISH6, DISH9, created), dishService.getByRestaurantId(2));
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + created);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void saveNotFoundRestaurant() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Not found restaurant with id=99"));
        dishService.save(DISH_NEW, 99);
    }

}
