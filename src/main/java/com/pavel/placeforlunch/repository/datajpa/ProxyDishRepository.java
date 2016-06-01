package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProxyDishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> getByRestaurantId(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1")
    int delete(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1 AND d.restaurant.id=?2")
    int deleteCheckRestaurant(int id, int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id=?1")
    Dish getWithRestaurant(int id);

    @SuppressWarnings("unchecked")
    @Transactional
    Dish save(Dish dish);

    @SuppressWarnings("SpringDataJpaMethodInconsistencyInspection")
    Dish findByIdAndRestaurantId(int id, int restaurantId);
}
