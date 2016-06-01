package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ProxyRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes WHERE r.id=?1")
    Restaurant getFetchDishes(int id);

    List<Restaurant> findAll();

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes ORDER BY r.id")
    List<Restaurant> getAllFetchDishes();

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int delete(int id);

    @SuppressWarnings("unchecked")
    @Transactional
    Restaurant save(Restaurant restaurant);
}
