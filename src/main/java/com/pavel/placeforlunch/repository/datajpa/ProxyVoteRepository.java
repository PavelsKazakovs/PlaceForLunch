package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProxyVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.restaurant=null")
    void resetAll();

    @Query("SELECT r, COUNT(v) FROM Vote v LEFT JOIN v.restaurant r GROUP BY r")
    List<Object[]> getVoteCountsByRestaurant();
}
