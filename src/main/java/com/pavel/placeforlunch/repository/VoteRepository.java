package com.pavel.placeforlunch.repository;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.model.Vote;

import java.util.Map;

public interface VoteRepository {

    Vote getByUserId(int userId);

    Vote save(Vote vote);

    void resetAll();

    Map<Restaurant, Integer> getVoteCountsByRestaurant();
}
