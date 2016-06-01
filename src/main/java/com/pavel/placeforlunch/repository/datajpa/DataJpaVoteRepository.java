package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    @Autowired
    private ProxyVoteRepository proxy;

    @Override
    public Vote getByUserId(int userId) {
        return proxy.findOne(userId);
    }

    @Override
    public Vote save(Vote vote) {
        return proxy.save(vote);
    }

    @Override
    public void resetAll() {
        proxy.resetAll();
    }

    @Override
    public Map<Restaurant, Integer> getVoteCountsByRestaurant() {
        Map<Restaurant, Integer> result = new HashMap<>();
        for (Object[] objects : proxy.getVoteCountsByRestaurant()) {
            Restaurant restaurant = objects[0] == null ? null : (Restaurant) objects[0];
            int voteCount = ((Long) objects[1]).intValue();
            result.put(restaurant, voteCount);
        }
        return result;
    }
}
