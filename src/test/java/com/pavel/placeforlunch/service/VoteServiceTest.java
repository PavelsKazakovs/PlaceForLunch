package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.RestaurantTestData;
import com.pavel.placeforlunch.UserTestData;
import com.pavel.placeforlunch.VoteTestData;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pavel.placeforlunch.RestaurantTestData.RESTAURANT2;
import static com.pavel.placeforlunch.RestaurantTestData.RESTAURANT_NOT_EXIST;
import static com.pavel.placeforlunch.UserTestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class VoteServiceTest extends ServiceTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    protected VoteService voteService;

    @Test
    public void get() throws Exception {
        Vote actual = voteService.getByUserId(USER_WITH_VOTE.getId());
        Vote expected = VoteTestData.USER_VOTE_WITH_RESTAURANT;
        VoteTestData.MATCHER.assertEquals(expected, actual);
        RestaurantTestData.MATCHER.assertEquals(expected.getRestaurant(), actual.getRestaurant());
        UserTestData.MATCHER.assertEquals(expected.getUser(), actual.getUser());
        LOG.debug("expected: " + expected);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getEmptyVote() throws Exception {
        Vote actual = voteService.getByUserId(USER_WITH_EMPTY_VOTE.getId());
        Assert.assertNull(actual.getRestaurant());
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        Vote actual = voteService.getByUserId(USER_NOT_EXIST.getId());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void cancel() throws Exception {
        voteService.cancel(USER_WITH_VOTE.getId());
        Assert.assertNull(voteService.getByUserId(USER_WITH_VOTE.getId()).getRestaurant());
    }

    @Test
    public void cancelNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        voteService.cancel(USER_NOT_EXIST.getId());
    }

    @Test
    public void vote() throws Exception {
        Vote returned = voteService.vote(RESTAURANT2.getId(), USER_WITH_EMPTY_VOTE.getId());
        Vote actual = voteService.getByUserId(USER_WITH_EMPTY_VOTE.getId());
        VoteTestData.MATCHER.assertEquals(VoteTestData.NEW_VOTE, returned);
        VoteTestData.MATCHER.assertEquals(VoteTestData.NEW_VOTE, actual);
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + VoteTestData.NEW_VOTE);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void voteNotFoundUser() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        voteService.vote(RESTAURANT2.getId(), USER_NOT_EXIST.getId());
    }

    @Test
    public void voteNotFoundRestaurant() {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found restaurant with id=" + RESTAURANT_NOT_EXIST.getId());
        voteService.vote(RESTAURANT_NOT_EXIST.getId(), USER_WITH_VOTE.getId());
    }

    @Test
    public void resetAll() throws Exception {
        voteService.resetAll();
        Map<Restaurant, Integer> actual = voteService.getVoteCountsByRestaurant();
        Map<Restaurant, Integer> expected = new HashMap<>();
        expected.put(null, ALL_USERS.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getVoteCountsByRestaurant() throws Exception {
        Map<Restaurant, Integer> actual = voteService.getVoteCountsByRestaurant();
        Map<Restaurant, Integer> expected = RestaurantTestData.VOTES_BY_RESTAURANT;
        Assert.assertEquals(expected.size(), actual.size());

        // Compare maps by comparing key sets and values separately. Both sets are ordered,
        // because both are LinkedHashMaps.
        List<Restaurant> expectedKeys = new ArrayList<>(expected.keySet());
        List<Restaurant> actualKeys = new ArrayList<>(actual.keySet());
        List<Integer> expectedValues = new ArrayList<>(expected.values());
        List<Integer> actualValues = new ArrayList<>(actual.values());

        RestaurantTestData.MATCHER.assertCollectionEquals(expectedKeys, actualKeys);
        Assert.assertEquals(expectedValues, actualValues);
        LOG.debug("expected: " + expected);
        LOG.debug("  actual: " + actual);
    }
}
