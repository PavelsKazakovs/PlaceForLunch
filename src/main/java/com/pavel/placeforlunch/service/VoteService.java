package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.util.exception.NotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Service for managing voting process.
 */
public interface VoteService {

    List<Restaurant> getOptions();

    Restaurant get(int userId) throws NotFoundException;

    void cancel(int userId) throws NotFoundException;

    Restaurant vote(int restaurantId, int userId) throws NotFoundException;

    // TODO: schedule once a day
    void deleteAll();

    Map<Restaurant, Integer> getVoteCountsByRestaurant();
}
