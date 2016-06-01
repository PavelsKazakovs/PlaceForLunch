package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.repository.RestaurantRepository;
import com.pavel.placeforlunch.repository.UserRepository;
import com.pavel.placeforlunch.repository.VoteRepository;
import com.pavel.placeforlunch.util.EntityName;
import com.pavel.placeforlunch.util.MapUtil;
import com.pavel.placeforlunch.util.exception.ExceptionUtil;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Vote getByUserId(int userId) throws ResourceNotFoundException {
        ExceptionUtil.check(userRepo.get(userId), EntityName.USER, userId);
        return voteRepo.getByUserId(userId);
    }

    @Transactional
    @Override
    public void cancel(int userId) throws ResourceNotFoundException {
        Vote vote = getByUserId(userId);
        vote.setRestaurant(null);
        voteRepo.save(vote);
    }

    @Transactional
    @Override
    public Vote vote(int restaurantId, int userId) throws ResourceNotFoundException {
        Vote vote = getByUserId(userId);
        Restaurant restaurant = ExceptionUtil.check(restaurantRepo.get(restaurantId), EntityName.RESTAURANT, restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepo.save(vote);
    }

    @Transactional
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void resetAll() {
        voteRepo.resetAll();
        System.out.println(Calendar.getInstance().getTime() + " / " + TimeZone.getDefault() + " : Votes were re-set");
    }

    @Override
    public Map<Restaurant, Integer> getVoteCountsByRestaurant() {
        Map<Restaurant, Integer> unsorted = voteRepo.getVoteCountsByRestaurant();
        return MapUtil.sortMap(unsorted, VoteStatsCmp);
    }

    /**
     * Comparator for sorting {@link LinkedHashMap} with vote stats.
     * Implements business logic of {@link VoteService#getVoteCountsByRestaurant()}.
     * Primary sort - vote count (desc), secondary sort - restaurant ID (asc).
     * Count of users that did not vote (key = null) is always last.
     */
    private static Comparator<Map.Entry<Restaurant, Integer>> VoteStatsCmp = (o1, o2) -> {
        if (o1.getKey() == null) {
            return 1;
        }
        if (o2.getKey() == null) {
            return -1;
        }

        int res = o2.getValue().compareTo(o1.getValue());
        return res != 0 ? res : o1.getKey().getId().compareTo(o2.getKey().getId());
    };
}
