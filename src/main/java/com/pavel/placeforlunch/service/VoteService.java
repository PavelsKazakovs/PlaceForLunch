package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;

import java.util.Map;

/**
 * Service for managing voting process.
 */
public interface VoteService {

    Vote getByUserId(int userId) throws ResourceNotFoundException;

    void cancel(int userId) throws ResourceNotFoundException;

    Vote vote(int restaurantId, int userId) throws ResourceNotFoundException;

    void resetAll();

    /**
     * Collects the voting results and returns these in sorted
     * {@link java.util.LinkedHashMap LinkedHashMap}
     * Primary sort - vote count (desc), secondary sort - restaurant ID (asc).
     * Count of users that did not vote (key: null) is always last.
     *
     * @return Sorted {@link java.util.LinkedHashMap LinkedHashMap} with
     * {@link Restaurant}s as keys and vote counts as values.
     */
    Map<Restaurant, Integer> getVoteCountsByRestaurant();
}
